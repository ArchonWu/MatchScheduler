package com.example.matchscheduler;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MatchEntryExtractor {
    private ArrayList<PlayerMatchEntry> playerMatchEntries;
    private String playerName;
    private String allInfoText;
    private String trimmedInfoText;
    private int totalUpcomingMatches;
    private String[] dividedUpcomingMatches;
    private String[] opponentNames;

    public MatchEntryExtractor(String playerName, String allInfoText) {
        this.playerMatchEntries = new ArrayList<>();
        this.playerName = playerName;
        this.allInfoText = allInfoText;
        this.trimmedInfoText = getTrimmedUpcomingText();
        this.totalUpcomingMatches = getTotalUpcomingMatches();
        this.dividedUpcomingMatches = getDividedUpcomingMatches();
        this.opponentNames = getOpponentNames();
    }

    protected ArrayList<PlayerMatchEntry> getPlayerMatchEntryList() {
        if (playerMatchEntries.size() == 0) {
            addAllUpcomingMatchesToList();
        }
        return playerMatchEntries;
    }

    protected String getLeftPlayer() {
        // left player is always the searched player by user
        return playerName;
    }

    protected void addAllUpcomingMatchesToList() {
        for (int i = 0; i < totalUpcomingMatches; i++) {
            PlayerMatchEntry playerMatchEntry =
                    new PlayerMatchEntry(getLeftPlayer(), "...", opponentNames[i], "tn000",
                            new Date(), new Time(0));
            playerMatchEntries.add(playerMatchEntry);
        }
    }

    protected String filterMatchDateTime(String jsonString) throws IOException {
        // TODO: default time are in UTC, need to convert
        String inHere = jsonString.substring(jsonString.indexOf("<span class=\\\"match-countdown\\\">"));
        String somewhere = inHere.substring(0, inHere.indexOf("</span>"));
        String morePrecise = somewhere.substring(somewhere.indexOf("<span class=\\\"timer-object timer-object-countdown-only\\\""), somewhere.indexOf(" <abbr"));
        String theActualDateTime = morePrecise.substring(morePrecise.lastIndexOf(">") + 1);

        return theActualDateTime;
    }

    protected String getTrimmedUpcomingText() {
        String trimmedText = allInfoText.substring(allInfoText.indexOf("Upcoming Matches"), allInfoText.indexOf("Recent Matches"));
        return trimmedText;
    }

    protected int getTotalUpcomingMatches() {
        String checkStr = trimmedInfoText;
        totalUpcomingMatches = checkStr.split("team-left").length - 1;
        return totalUpcomingMatches;
    }

    protected String[] getDividedUpcomingMatches() {
        if (dividedUpcomingMatches == null) {
            String[] split = trimmedInfoText.split("team-left", 0);
            String[] divided = Arrays.copyOfRange(split, 1, split.length);
            return divided;
        } else return dividedUpcomingMatches;
    }

    protected String[] getOpponentNames() {
            String[] opponentNames = new String[totalUpcomingMatches];
            for (int i = 0; i < totalUpcomingMatches; i++) {
                String temp = dividedUpcomingMatches[i].substring(0, dividedUpcomingMatches[i].lastIndexOf("</a></span></td>"));
                temp = temp.substring(temp.lastIndexOf(">"));
                temp = temp.substring(1);
                opponentNames[i] = temp;
            }
            return opponentNames;
    }

    protected String[] getTournamentNames(){
        //TODO: !
        return null;
    }
}

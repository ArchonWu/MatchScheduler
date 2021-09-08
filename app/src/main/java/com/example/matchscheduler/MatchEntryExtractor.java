package com.example.matchscheduler;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class MatchEntryExtractor {
    private ArrayList<PlayerMatchEntry> playerMatchEntries;
    private String playerName;
    private String allInfoText;
    private String trimmedInfoText;
    private int totalUpcomingMatches;
    private String[] dividedUpcomingMatches;

    public MatchEntryExtractor(String playerName, String allInfoText) {
        this.playerMatchEntries = new ArrayList<>();
        this.playerName = playerName;
        this.allInfoText = allInfoText;
        this.trimmedInfoText = getTrimmedUpcomingText();
        this.totalUpcomingMatches = getTotalUpcomingMatches();
        this.dividedUpcomingMatches = getDividedUpcomingMatches();
    }

    protected ArrayList getPlayerMatchEntryList() {
        return playerMatchEntries;
    }

    protected String getLeftPlayer() {
        // left player is always the searched player by user
        return playerName;
    }

    protected void addAllUpcomingMatchesToList() {
        for (int i = 0; i < totalUpcomingMatches; i++) {

        }
    }

    private void addMatchEntryToList(String playerRace, String opponentName, Date matchDate, Time matchTime) {
        PlayerMatchEntry playerMatchEntry = new PlayerMatchEntry(playerName, playerRace, opponentName, "", matchDate, matchTime);
        playerMatchEntries.add(playerMatchEntry);
    }

    // only for one single match
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
        String[] divided = trimmedInfoText.split("team-left", 0);
        return divided;
    }
}

package com.example.matchscheduler;

import android.annotation.SuppressLint;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class MatchEntryExtractor {
    private ArrayList<PlayerMatchEntry> playerMatchEntries;
    private String playerName;
    private String allInfoText;
    private String trimmedInfoText;
    private int totalUpcomingMatches;
    private String[] dividedUpcomingMatches;
    private String[] opponentNames;
    private String[] tournamentNames;
    private String[] dates;
    private boolean init;

    public MatchEntryExtractor(String playerName, String allInfoText) {
        initiate(playerName, allInfoText);
    }

    private void initiate(String playerName, String allInfoText) {
        this.playerMatchEntries = new ArrayList<>();
        this.playerName = playerName;
        this.allInfoText = allInfoText;
        try {
            this.trimmedInfoText = getTrimmedUpcomingText();
        } catch (ProcessingDataException e) {
            playerMatchEntries.add(new PlayerMatchEntry("", "", "",
                    "No Upcoming Matches Found", "", new Time(0)));
            this.init = true;
            e.printStackTrace();
        }
        this.totalUpcomingMatches = getTotalUpcomingMatches();
        this.dividedUpcomingMatches = getDividedUpcomingMatches();
        this.opponentNames = getOpponentNames();
        this.tournamentNames = getTournamentNames();
        this.dates = getDateTime();
        this.init = true;
    }

    protected ArrayList<PlayerMatchEntry> getPlayerMatchEntryList() {
        if (playerMatchEntries.size() == 0 && init) {
            addAllUpcomingMatchesToList();
            return playerMatchEntries;
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
                    new PlayerMatchEntry(getLeftPlayer(), "...", opponentNames[i], tournamentNames[i],
                            dates[i].toString(), new Time(0));
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

    protected String getTrimmedUpcomingText() throws ProcessingDataException {
        if (init) return trimmedInfoText;
        int upcomingIndex = allInfoText.indexOf("Upcoming Matches");
        int recentIndex = allInfoText.indexOf("Recent Matches");
        if (upcomingIndex < 0) throw new ProcessingDataException("Player has no upcoming matches!");
        String trimmedText = allInfoText.substring(upcomingIndex, recentIndex);
        return trimmedText;

    }

    protected int getTotalUpcomingMatches() {
        if (init) return totalUpcomingMatches;
        String checkStr = trimmedInfoText;
        totalUpcomingMatches = checkStr.split("team-left").length - 1;
        return totalUpcomingMatches;
    }

    protected String[] getDividedUpcomingMatches() {
        if (init) return dividedUpcomingMatches;
        String[] split = trimmedInfoText.split("team-left", 0);
        String[] divided = Arrays.copyOfRange(split, 1, split.length);
        return divided;
    }

    protected String[] getOpponentNames() {
        if (init) return opponentNames;
        String[] opponentNames = new String[totalUpcomingMatches];
        for (int i = 0; i < totalUpcomingMatches; i++) {
            String temp = dividedUpcomingMatches[i].substring(0, dividedUpcomingMatches[i].lastIndexOf("</a></span></td>"));
            temp = temp.substring(temp.lastIndexOf(">"));
            temp = temp.substring(1);
            opponentNames[i] = temp;
        }
        return opponentNames;
    }

    protected String[] getTournamentNames() {
        if (init) return tournamentNames;
        String[] tournamentNames = new String[totalUpcomingMatches];
        for (int i = 0; i < totalUpcomingMatches; i++) {
            String targetStr = dividedUpcomingMatches[i];
            String key = "</a>&#160;</div></";
            String temp = targetStr.substring(0, targetStr.lastIndexOf(key));
            temp = temp.substring(temp.lastIndexOf("\\\">"));
            temp = temp.substring(3);
            tournamentNames[i] = temp;
        }
        return tournamentNames;
    }

    protected String[] getDateTime() {
        if (init) return dates;
        dates = new String[totalUpcomingMatches];
//        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy - HH:mm");
        for (int i = 0; i < totalUpcomingMatches; i++) {
            String dateTime = "error";
            dateTime = dividedUpcomingMatches[i];
            dateTime = dateTime.substring(0, dateTime.indexOf("<abbr data-tz="));
            dateTime = dateTime.substring(dateTime.lastIndexOf("\">") + 2);
//            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//            try {
//                calendar.setTime(Objects.requireNonNull(sdf.parse(dateTime)));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            calendar.setTimeZone(TimeZone.getTimeZone("PDT"));
//            dates[i] = calendar.getTime().toString();
            dates[i] = dateTime;
        }
        return dates;
    }
}

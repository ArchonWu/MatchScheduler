package com.example.matchscheduler;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/*
    Display info of results shown in a player's upcoming match by row
 */
public class MatchEntryExtractor {
    private ArrayList<PlayerMatchEntry> playerMatchEntries;
    private String playerName;
    private String allInfoText;
    private String trimmedInfoText;
    private int totalUpcomingMatches;
    private String[] dividedUpcomingMatches;
    private String[] opponentNames;
    private String[] tournamentNames;
    private ArrayList<java.util.Date> matchDates;
    private String[] dates;
    private ArrayList<String> times;
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
                    "No Upcoming Matches Found", null, "", ""));
            this.init = true;
            e.printStackTrace();
        }
        this.totalUpcomingMatches = getTotalUpcomingMatches();
        this.dividedUpcomingMatches = getDividedUpcomingMatches();
        this.opponentNames = getOpponentNames();
        this.tournamentNames = getTournamentNames();
        this.matchDates = getMatchDates();
        this.dates = getDatesOfEntry();
        this.times = getTimesOfEntry();
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
            //TODO: add Date field and optimize dates and times processing
            PlayerMatchEntry playerMatchEntry =
                    new PlayerMatchEntry(getLeftPlayer(), "...", opponentNames[i], tournamentNames[i],
                            null, dates[i], times.get(i));
            playerMatchEntries.add(playerMatchEntry);
        }
    }

    protected String filterMatchDateTime(String jsonString) throws IOException {
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

    protected ArrayList<Date> getMatchDates() {
        matchDates = new ArrayList<>();
        for (int i = 0; i < totalUpcomingMatches; i++) {
            String matchDateString = dividedUpcomingMatches[i];
            matchDateString = matchDateString.substring(0, matchDateString.indexOf("<abbr data-tz="));
            matchDateString = matchDateString.substring(matchDateString.lastIndexOf("\">") + 2);
            Date matchDate = stringToDate(matchDateString);
            matchDates.add(matchDate);
        }
        return matchDates;
    }

    // helper for getMatchDates()
    private Date stringToDate(String dateString) {
        // Janku: https://stackoverflow.com/questions/8573250/android-how-can-i-convert-string-to-date
        Date theDate = null;
        try {
            String formatString = "MMM dd, yyyy - HH:mm";   // "September 13, 2021 - 9:30"
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
            theDate = simpleDateFormat.parse(dateString);   // Mon Sep 13 09:30:00 PDT 2021
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return theDate;
    }

    // TODO: update this with Date
    protected String[] getDatesOfEntry() {
        if (init) return dates;
        dates = new String[totalUpcomingMatches];
        for (int i = 0; i < totalUpcomingMatches; i++) {
            String dateTime = dividedUpcomingMatches[i];
            dateTime = dateTime.substring(0, dateTime.indexOf("<abbr data-tz="));
            dateTime = dateTime.substring(dateTime.lastIndexOf("\">") + 2);
            String dateYear = dateTime.substring(0, dateTime.indexOf(" - "));
            dates[i] = dateYear;
        }
        return dates;
    }

    protected ArrayList<String> getTimesOfEntry(){
        ArrayList<String> times = new ArrayList<>();
        for (int i = 0; i < matchDates.size(); i++){
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
            Date matchDate = matchDates.get(i);
            String matchTimeString = outputFormat.format(matchDate);
            System.out.println("matchDate in getTimesOfEntry(): " + matchTimeString);
            times.add(matchTimeString);
        }
        return times;
    }

}

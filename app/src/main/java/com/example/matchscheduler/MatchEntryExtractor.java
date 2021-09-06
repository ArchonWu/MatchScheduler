package com.example.matchscheduler;

import android.content.Context;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MatchEntryExtractor {
    private ArrayList<PlayerMatchEntry> playerMatchEntries;
    private String playerName;
    private String allInfoText;
    private String trimmedInfoText;

    public MatchEntryExtractor(String playerName, String allInfoText) {
        this.playerMatchEntries = new ArrayList<>();
        this.playerName = playerName;
        this.allInfoText = allInfoText;
        this.trimmedInfoText = getTrimmedInfoText();
    }

    public ArrayList getPlayerMatchEntryList() {
        return playerMatchEntries;
    }

    public String getLeftPlayer() {
        // left player is always the searched player by user
        return playerName;
    }

    public void addAllUpcomingMatchesToList() {

    }

    public void addMatchEntryToList(String playerRace, String opponentName, Date matchDate, Time matchTime) {


        PlayerMatchEntry playerMatchEntry = new PlayerMatchEntry(playerName, playerRace, opponentName, matchDate, matchTime);
        playerMatchEntries.add(playerMatchEntry);
    }

    // only for one single match
    public String filterMatchDateTime(String jsonString) throws IOException {
        // TODO: default time are in UTC, need to convert
        String inHere = jsonString.substring(jsonString.indexOf("<span class=\\\"match-countdown\\\">"));
        String somewhere = inHere.substring(0, inHere.indexOf("</span>"));
        String morePrecise = somewhere.substring(somewhere.indexOf("<span class=\\\"timer-object timer-object-countdown-only\\\""), somewhere.indexOf(" <abbr"));
        String theActualDateTime = morePrecise.substring(morePrecise.lastIndexOf(">") + 1);

        return theActualDateTime;
    }

    public String getTrimmedInfoText() {
        String trimmedText = allInfoText.substring(allInfoText.indexOf("Upcoming Matches"), allInfoText.indexOf("Recent Matches"));
        return trimmedText;
    }
}

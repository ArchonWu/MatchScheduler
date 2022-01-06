package com.example.matchscheduler;

import android.util.JsonWriter;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

public class PlayerMatchEntry {
    private String playerName;
    private String playerRace;
    private String opponentName;
    private String tournamentName;
    private Date matchDate;
    private String date;
    private String time;
    private boolean isAdded;

    public PlayerMatchEntry(String playerName, String playerRace, String opponent,
                            String tournamentName, Date matchDate, String date, String time) {
        this.playerName = playerName;
        this.playerRace = playerRace;
        this.opponentName = opponent;
        this.tournamentName = tournamentName;
        this.matchDate = matchDate;
        this.date = date;
        this.time = time;
        this.isAdded = false;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerRace() {
        return playerRace;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public boolean getIsAdded() {
        return isAdded;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlayerRace(String playerRace) {
        this.playerRace = playerRace;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setIsAdded(boolean isAdded) {
        this.isAdded = isAdded;
    }

    public void writeToJson(JsonWriter writer) throws IOException {
        writer.beginObject();
        writer.name("playerName").value(playerName);
        writer.name("opponentName").value(opponentName);
        writer.name("tournamentName").value(tournamentName);
        writer.name("date").value(String.valueOf(date));
        writer.endObject();
    }
}

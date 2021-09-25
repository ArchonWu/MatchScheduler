package com.example.matchscheduler;

import java.sql.Time;
import java.util.Date;

public class PlayerMatchEntry {
    private String playerName;
    private String playerRace;
    private String opponentName;
    private String tournamentName;
    private Date date;
    private Time time;
    private boolean isAdded;

    public PlayerMatchEntry(String playerName, String playerRace, String opponent,
                            String tournamentName, Date date, Time time) {
        this.playerName = playerName;
        this.playerRace = playerRace;
        this.opponentName = opponent;
        this.tournamentName = tournamentName;
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

    public Date getDate() {
        return date;
    }

    public Time getTime() {
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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setIsAdded(boolean isAdded) {
        this.isAdded = isAdded;
    }
}

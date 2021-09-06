package com.example.matchscheduler;

import java.sql.Time;
import java.util.Date;

public class PlayerMatchEntry {
    private String playerName;
    private String playerRace;
    private String opponentName;
    private Date date;
    private Time time;

    public PlayerMatchEntry(String playerName, String playerRace, String opponent, Date date, Time time) {
        this.playerName = playerName;
        this.playerRace = playerRace;
        this.opponentName = opponent;
        this.date = date;
        this.time = time;
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

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
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
}

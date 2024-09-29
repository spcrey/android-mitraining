package com.example.homework_day_04;

public class GameBean {
    private String gameName;
    private int gameIcon;

    private String gameStatus;

    public GameBean(){

    }

    public GameBean(String gameName, int gameIcon, String gameStatus) {
        this.gameName = gameName;
        this.gameIcon = gameIcon;
        this.gameStatus = gameStatus;
    }

    public int getGameIcon() {
        return gameIcon;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setGameIcon(int gameIcon) {
        this.gameIcon = gameIcon;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }
}

package com.example.homework_day_09.tools;

import androidx.annotation.NonNull;

public class GameItem {
    public String gameName;
    public String packageName;

    public GameItem(String gameName, String packageName) {
        this.gameName = gameName;
        this.packageName = packageName;
    }

    @NonNull
    @Override
    public String toString() {
        return "GameItem{" +
                "gameName='" + gameName + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }
}

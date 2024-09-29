package com.example.homework_day_10;

import androidx.annotation.NonNull;

public class GameItem {
    public String gameName;
    public String packageName;

    @NonNull
    @Override
    public String toString() {
        return "GameItem{" +
                "gameName='" + gameName + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }
}


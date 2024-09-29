package com.example.homework_day_09.item;

import androidx.annotation.NonNull;

public class QuickGameItem {
    private final String gameName;
    private final String packageName;
    private final String versionName;

    public QuickGameItem(String gameName, String packageName, String versionName) {
        this.gameName = gameName;
        this.packageName = packageName;
        this.versionName = versionName;
    }

    public String getGameName() {
        return gameName;
    }
    public String getPackageName() {
        return packageName;
    }
    public String getVersionName() {
        return versionName;
    }

    @NonNull
    @Override
    public String toString() {
        return "GameItem{" +
                "gameName='" + gameName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionName='" + versionName + '\'' +
                '}';
    }
}

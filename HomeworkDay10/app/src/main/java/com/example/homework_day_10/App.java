package com.example.homework_day_10;

import android.app.Application;

import com.github.anrwatchdog.ANRWatchDog;

public class App extends Application {
    @Override
    public void onCreate() {
//        new ANRWatchDog().start();
        super.onCreate();
    }
}

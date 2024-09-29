package com.example.homework_day_01;

import android.app.Application;
import android.util.Log;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("tag", "abc");
    }
}


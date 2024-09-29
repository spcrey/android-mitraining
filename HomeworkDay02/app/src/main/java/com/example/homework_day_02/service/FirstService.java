package com.example.homework_day_02.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class FirstService extends IntentService {
    public static final String TAG = "FirstService";

    public FirstService() {
        super("FirstService");
        Log.d(TAG, "FirstService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Log.d(TAG, "onHandleIntent");
            Thread.sleep(5000);
            Log.d(TAG, "sleep finish");
        } catch (InterruptedException e) {
            Log.e(TAG, "error", e);
        }
    }
}
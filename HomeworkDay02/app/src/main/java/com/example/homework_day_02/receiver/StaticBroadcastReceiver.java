package com.example.homework_day_02.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StaticBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "StaticBroadReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
    }
}
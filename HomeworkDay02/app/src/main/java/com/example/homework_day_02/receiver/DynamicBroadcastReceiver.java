package com.example.homework_day_02.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

public class DynamicBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "DynamicBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            Toast.makeText(context, "network changed", Toast.LENGTH_SHORT).show();
        }
    }
}
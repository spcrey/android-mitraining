package com.example.homework_day_02.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BindService extends Service {
    private static final String TAG = "BindService";
    private final LocalBinder mBinder = new LocalBinder();
    public BindService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "IBinder");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    public String getBindText() {
        return "Hello World";
    }

    public class LocalBinder extends Binder {
        public BindService getService() {
            return BindService.this;
        }
    }
}
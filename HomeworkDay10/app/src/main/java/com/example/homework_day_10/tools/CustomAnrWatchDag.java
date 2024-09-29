package com.example.homework_day_10.tools;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class CustomAnrWatchDag {

    private static final String TAG = "CustomAnrWatchDag";

    private final Handler handler = new Handler(Looper.getMainLooper());

    private volatile long tick = 0;

    private boolean runFlag = true;
    private final int interval;
    private final int checkCount;
    private int count = 0;
    private final Runnable ticker = new Runnable() {
        @Override public void run() {
            tick = 0;
        }
    };

    private final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while(runFlag) {
                tick = 1;
                handler.post(ticker);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (tick == 0) {
                    Log.d(TAG, "on responsive");
                    count = 0;
                } else {
                    Log.d(TAG, "not responsive");
                    count ++;
                }
                if (count == checkCount) {
                    throw new IllegalArgumentException("CustomAnrWatchDag: error");
                }
            }
        }
    });

    public CustomAnrWatchDag(int checkCount, int interval) {
        this.checkCount = checkCount;
        this.interval = interval;
    }

    public void start() {
        thread.start();
    }

    public void pause() {
        runFlag = false;
    }

    public void stop() {
        pause();
        thread.stop();
    }
}

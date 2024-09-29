package com.example.homework_day_10.tools;

import android.content.Context;

public class Singleton {
    private static volatile Singleton instance;
    private Context context;

    private Singleton(Context context) {
        this.context = context;
    }

    private static Singleton getInstance(Context context) {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton(context);
                }
            }
        }
        return instance;
    }
}

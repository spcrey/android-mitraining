package com.example.homework_day_02.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.homework_day_02.ICalculatorInterface;

public class CalculatorService extends Service {
    private static final String TAG = "CalculatorService";
    private final ICalculatorInterface.Stub mBinder = new ICalculatorInterface.Stub() {
        @Override
        public int add(int a, int b) throws RemoteException {
            return a + b;
        }
    };

    public CalculatorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "IBinder");
        return mBinder;
    }
}
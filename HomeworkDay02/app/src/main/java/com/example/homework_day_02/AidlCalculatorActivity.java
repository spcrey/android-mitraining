package com.example.homework_day_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.homework_day_02.service.CalculatorService;


public class AidlCalculatorActivity extends AppCompatActivity {

    private static final String TAG = "AidlCalculatorActivity";
    private ICalculatorInterface mBinderService;
    private boolean mBound = false;
    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderService = ICalculatorInterface.Stub.asInterface(service);
            mBound = true;
            Log.d(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            Log.d(TAG, "onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_calculator);

        Intent intent = new Intent(this, CalculatorService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        Button calculateBtn = findViewById(R.id.btn_calculate);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBound) {
                    return;
                }
                int num = 0;
                try {
                    num = mBinderService.add(1, 2);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(AidlCalculatorActivity.this, "result=" + num, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        mBound = false;
    }
}
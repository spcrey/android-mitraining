package com.example.homework_day_02;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homework_day_02.service.BindService;

public class ServiceBindActivity extends AppCompatActivity {
    private static final String TAG = "ServiceBindActivity";
    private BindService mBindService;
    private boolean mBound;
    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BindService.LocalBinder localBinder = (BindService.LocalBinder) service;
            mBindService = localBinder.getService();
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
        setContentView(R.layout.activity_service_bind);
        Button bindBtn = findViewById(R.id.btn_bind);
        Button unbindBtn = findViewById(R.id.btn_unbind);
        Button getBtn = findViewById(R.id.btn_get);

        bindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceBindActivity.this, BindService.class);
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            }
        });

        unbindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound) {
                    unbindService( mConnection);
                    mBound = false;
                }
                else { }
            }
        });

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBound) {
                    return;
                }
                String bindText = mBindService.getBindText();
                Toast.makeText(ServiceBindActivity.this, bindText, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.homework_day_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.homework_day_02.service.FirstService;

public class ServiceActivity extends AppCompatActivity {

    private static final String TAG = "ServiceActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_service);

        Button button = findViewById(R.id.btn_start_service);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceActivity.this, FirstService.class);
            startService(intent);
        });
    }
}
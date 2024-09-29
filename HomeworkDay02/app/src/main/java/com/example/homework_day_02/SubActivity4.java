package com.example.homework_day_02;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity4 extends AppCompatActivity {

    private static final String TAG = "SubActivity4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_sub4);
    }

    public void onButtonClick(View view) {
        Log.d(TAG, "onButtonClick");
        Intent intent = new Intent(this, SubActivity1.class);
        startActivity(intent);
    }
}
package com.example.homework_day_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SubActivity2 extends AppCompatActivity {

    private static final String TAG = "SubActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_sub2);
    }

    public void onButtonClick(View view) {
        Log.d(TAG, "onButtonClick");
        Intent intent = new Intent(this, SubActivity1.class);
        startActivity(intent);
    }
}
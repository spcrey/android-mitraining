package com.example.home_workday_05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.home_workday_05.fragment.RelativeLayoutFragment;
import com.example.home_workday_05.fragment.ViewStubFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button;
        button = findViewById(R.id.btn_to_homework);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (v.getId() == R.id.btn_to_homework) {
            intent = new Intent(MainActivity.this, HomeworkActivity.class);
        }
        startActivity(intent);
    }
}
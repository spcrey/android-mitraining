package com.example.homework_day_04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        Button button;

        findViewById(R.id.btn_to_multi_view_activity)
                .setOnClickListener(this);

        button = findViewById(R.id.btn_to_marquee_activity);
        button.setOnClickListener(this);

        button = findViewById(R.id.btn_to_multi_button_activity);
        button.setOnClickListener(this);

        button = findViewById(R.id.btn_to_radio_group_activity);
        button.setOnClickListener(this);

        button = findViewById(R.id.btn_to_check_box);
        button.setOnClickListener(this);

        button = findViewById(R.id.btn_to_toggle_switch);
        button.setOnClickListener(this);

        button = findViewById(R.id.btn_to_seekbar_image);
        button.setOnClickListener(this);

        button = findViewById(R.id.btn_to_recycler_view);
        button.setOnClickListener(this);

        button = findViewById(R.id.btn_to_text_check);
        button.setOnClickListener(this);

        button = findViewById(R.id.btn_to_span_text);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (v.getId() == R.id.btn_to_marquee_activity) {
            intent = new Intent(MainActivity.this, MarqueeActivity.class);
        } else if (v.getId() == R.id.btn_to_multi_button_activity) {
            intent = new Intent(MainActivity.this, MultiButtonActivity.class);
        } else if (v.getId() == R.id.btn_to_radio_group_activity) {
            intent = new Intent(MainActivity.this, RadioGroupActivity.class);
        } else if (v.getId() == R.id.btn_to_check_box) {
            intent = new Intent(MainActivity.this, CheckBoxActivity.class);
        } else if (v.getId() == R.id.btn_to_toggle_switch) {
            intent = new Intent(MainActivity.this, ToggleSwitchActivity.class);
        } else if (v.getId() == R.id.btn_to_seekbar_image) {
            intent = new Intent(MainActivity.this, SeekbarImageActivity.class);
        } else if (v.getId() == R.id.btn_to_recycler_view) {
            intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
        } else if (v.getId() == R.id.btn_to_text_check) {
            intent = new Intent(MainActivity.this, TextCheckActivity.class);
        } else if (v.getId() == R.id.btn_to_span_text) {
            intent = new Intent(MainActivity.this, SpanTextActivity.class);
        } else if (v.getId() == R.id.btn_to_multi_view_activity) {
            intent = new Intent(MainActivity.this, MultiViewActivity.class);
        }
        startActivity(intent);
    }
}
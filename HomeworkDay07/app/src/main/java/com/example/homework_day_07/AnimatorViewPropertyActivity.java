package com.example.homework_day_07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AnimatorViewPropertyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_view_property);

        TextView textView = findViewById(R.id.view_property_text);
        textView.animate()
                .rotationX(360)
                .translationX(100)
                .setDuration(1000)
                .setStartDelay(1000)
                .start();
    }
}
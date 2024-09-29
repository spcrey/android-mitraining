package com.example.homework_day_07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.homework_day_07.fragment.TextFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private void setButtonOnClickListener() {
        Button button;
        button = findViewById(R.id.to_test_activity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InterpolatorActivity.class);
                startActivity(intent);
            }
        });
        button = findViewById(R.id.to_homework_tween_animation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeworkTweenAnimationActivity.class);
                startActivity(intent);
            }
        });
        button = findViewById(R.id.to_homework_property_animation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeworkPropertyAnimationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addFragmentManager() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.test_fragment_container_view, TextFragment.class, null)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragmentManager();
        setButtonOnClickListener();
    }
}
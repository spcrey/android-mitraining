package com.example.homework_day_09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.homework_day_09.fragment.TestFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment();
        setButtonOnClickListener();
    }

    private void addFragment() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.test_fragment_container_view, TestFragment.class, null)
                .commit();
    }

    private void setButtonOnClickListener() {
        Button button;
        button = findViewById(R.id.to_test_activity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeworkActivity.class);
                startActivity(intent);
            }
        });
    }
}

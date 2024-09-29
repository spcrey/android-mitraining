package com.example.homework_day_08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.homework_day_08.fragment.TestFragment;
import com.example.homework_day_08.tools.TestKotlin;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainFragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragmentManager();
        setButtonOnClickListener();
        TestKotlin testKotlin = new TestKotlin();
        Log.d(TAG, String.valueOf(testKotlin.sum(1,2)));
    }


    private void addFragmentManager() {
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
package com.example.homework_day_10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.homework_day_10.fragment.TestFragment;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    public static List<TestActivity> leakageActivities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leakageActivities.add(this);
        setContentView(R.layout.activity_test);
    }
}
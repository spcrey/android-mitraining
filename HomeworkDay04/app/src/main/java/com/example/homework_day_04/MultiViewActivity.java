package com.example.homework_day_04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.homework_day_04.fragment.MarqueeFragment;
import com.example.homework_day_04.fragment.MultiViewFragment;

public class MultiViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_view);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.multi_view_fragment_container_view, MultiViewFragment.class, null)
                .commit();
    }
}
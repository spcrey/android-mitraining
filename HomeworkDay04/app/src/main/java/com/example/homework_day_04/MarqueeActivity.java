package com.example.homework_day_04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.homework_day_04.R;
import com.example.homework_day_04.fragment.MarqueeFragment;

public class MarqueeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.marquee_fragment_container_view, MarqueeFragment.class, null)
                .commit();
    }
}
package com.example.homework_day_06;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.homework_day_06.fragment.HomeFragment;
import com.example.homework_day_06.fragment.MainFragment;
import com.example.homework_day_06.fragment.MineFragment;

public class HomeworkActivity extends AppCompatActivity {

    private static final String TAG = "HomeworkActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.page_fragment_container_view, HomeFragment.class, null, "BUTTON_FRAGMENT_TAG")
                .commit();

        Button buttonToPageHome = findViewById(R.id.to_page_home);
        Button buttonToPageMine = findViewById(R.id.to_page_mine);

        buttonToPageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "to_page_home");
                getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.page_fragment_container_view, HomeFragment.class, null, "BUTTON_FRAGMENT_TAG")
                        .commit();
                buttonToPageHome.setBackgroundColor(getResources().getColor(R.color.coral));
                buttonToPageMine.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        });

        buttonToPageMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "to_page_mine");
                getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.page_fragment_container_view, MineFragment.class, null, "BUTTON_FRAGMENT_TAG")
                        .commit();
                buttonToPageHome.setBackgroundColor(getResources().getColor(R.color.gray));
                buttonToPageMine.setBackgroundColor(getResources().getColor(R.color.coral));
            }
        });
    }
}
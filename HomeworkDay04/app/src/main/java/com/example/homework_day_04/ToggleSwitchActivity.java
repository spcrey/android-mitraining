package com.example.homework_day_04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ToggleSwitchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle_switch);

        ToggleButton toggleButton = findViewById(R.id.toggle_button);
        Switch switch_slider = findViewById(R.id.switch_slider);
        switch_slider.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleButton.setPressed(isChecked);
                Toast.makeText(ToggleSwitchActivity.this, "status: " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
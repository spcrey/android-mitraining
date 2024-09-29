package com.example.homework_day_04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class CheckBoxActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);

        CheckBox checkBox = findViewById(R.id.select_apple);
        checkBox.setOnCheckedChangeListener(this);
        checkBox = findViewById(R.id.select_banana);
        checkBox.setOnCheckedChangeListener(this);
        checkBox = findViewById(R.id.select_watermelon);
        checkBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Toast.makeText(this, "select fruit: " + buttonView.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
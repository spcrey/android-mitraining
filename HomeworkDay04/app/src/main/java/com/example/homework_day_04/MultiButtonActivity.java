package com.example.homework_day_04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homework_day_04.R;

public class MultiButtonActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_button);

        Button button;
        button = findViewById(R.id.btn_click);
        button.setOnClickListener(this);
        button = findViewById(R.id.btn_long_click);
        button.setOnLongClickListener(this);
        button = findViewById(R.id.btn_touch);
        button.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_click) {
            Toast.makeText(this, "onClick", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.btn_long_click) {
            Toast.makeText(this, "onLongClick", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.btn_touch) {
            Toast.makeText(this, "onTouch", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
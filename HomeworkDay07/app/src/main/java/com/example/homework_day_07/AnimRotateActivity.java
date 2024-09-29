package com.example.homework_day_07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AnimRotateActivity extends AppCompatActivity {

    private static final String TAG = "AnimRotateActivity";

    private Animation load_static() {
        return AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_rotate);
        ImageView imageView  = findViewById(R.id.rotate_robot);
        imageView.startAnimation(load_static());
    }
}
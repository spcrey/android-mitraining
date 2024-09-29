package com.example.homework_day_07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AnimScaleActivity extends AppCompatActivity {

    private static final String TAG = "AnimScaleActivity";

    private Animation load_static() {
        return AnimationUtils.loadAnimation(this, R.anim.anim_scale);
    }

    private Animation load_dynamic() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_scale);

        ImageView imageView  = findViewById(R.id.scale_robot);

        imageView.startAnimation(load_static());
    }
}
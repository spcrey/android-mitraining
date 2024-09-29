package com.example.homework_day_07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AnimAlphaActivity extends AppCompatActivity {

    private static final String TAG = "AnimAlphaActivity";

    private Animation load_static() {
        return AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
    }

    private Animation load_dynamic() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(
                0f, 1f
        );
        alphaAnimation.setDuration(1000);
        return alphaAnimation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_alpha);
        ImageView imageView  = findViewById(R.id.alpha_robot);
        imageView.startAnimation(load_dynamic());
    }
}
package com.example.homework_day_07;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class InterpolatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolator);

        ImageView imageView = findViewById(R.id.translate_robot);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(
                imageView, View.TRANSLATION_X, 0f, 400f
        );
        objectAnimator.setDuration(3000);
        objectAnimator.setStartDelay(1000);
        Interpolator interpolator;
        interpolator = new AccelerateInterpolator();
        interpolator = new AccelerateDecelerateInterpolator();
        objectAnimator.setInterpolator(interpolator);
        objectAnimator.start();
    }
}
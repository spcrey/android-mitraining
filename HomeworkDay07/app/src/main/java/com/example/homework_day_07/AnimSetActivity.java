package com.example.homework_day_07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class AnimSetActivity extends AppCompatActivity {

    private static final String TAG = "AnimSetActivity";

    private RotateAnimation load_anim_rotate() {
        RotateAnimation rotateAnimation = new RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setRepeatCount(5);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(TAG, "anim rotate start");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG, "anim rotate end");
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d(TAG, "anim rotate repeat");
            }
        });
        return rotateAnimation;
    }

    private AlphaAnimation load_anim_alpha() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(
                1f, 0f
        );
        alphaAnimation.setRepeatCount(5);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(TAG, "anim alpha start");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG, "anim alpha end");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d(TAG, "anim alpha repeat");
            }
        });
        return alphaAnimation;
    }

    private ScaleAnimation load_anim_scale() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1f, 1.2f, 1f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(TAG, "anim scale start");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG, "anim scale end");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d(TAG, "anim scale repeat");
            }
        });
        return scaleAnimation;
    }

    private Animation load_dynamic() {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(load_anim_scale());
        animationSet.addAnimation(load_anim_rotate());
        animationSet.addAnimation(load_anim_alpha());
        animationSet.setDuration(1000);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(TAG, "anim set start");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG, "anim set end");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d(TAG, "anim set repeat");
            }
        });

        return animationSet;
    }

    private Animation load_static() {
        return AnimationUtils.loadAnimation(this, R.anim.anim_set);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_set);

        ImageView imageView  = findViewById(R.id.set_robot);

        imageView.startAnimation(load_dynamic());
    }
}
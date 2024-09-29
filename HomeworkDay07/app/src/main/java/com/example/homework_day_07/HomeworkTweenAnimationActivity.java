package com.example.homework_day_07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

public class HomeworkTweenAnimationActivity extends AppCompatActivity {

    private static final String TAG = "HomeworkTweenAnimationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_tween_animation);
        startAnimation();
    }

    private void startAnimation() {
        TextView textView = findViewById(R.id.rectangle_color_block);
        Animation animationSet = load_anim_set();
        animationSet.setStartOffset(1000);
        textView.startAnimation(animationSet);
    }

    private Animation load_anim_set() {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(load_anim_rotate());
        animationSet.addAnimation(load_anim_alpha());
        animationSet.setDuration(2000);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(TAG, "[anim set] start");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG, "[anim set] end");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d(TAG, "[anim set] repeat");
            }
        });
        return animationSet;
    }

    private Animation load_anim_alpha() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(
                1f, 0.8f
        );
        alphaAnimation.setRepeatCount(2);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(TAG, "[anim alpha] start");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG, "[anim alpha] end");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d(TAG, "[anim alpha] repeat");
            }
        });
        return alphaAnimation;
    }

    private Animation load_anim_rotate() {
        RotateAnimation rotateAnimation = new RotateAnimation(
                0f, -720f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setRepeatCount(2);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(TAG, "[anim rotate] start");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG, "[anim rotate] end");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d(TAG, "[anim rotate] repeat");
            }
        });
        return rotateAnimation;
    }
}
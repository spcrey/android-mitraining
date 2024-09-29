package com.example.homework_day_07;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BaseInterpolator;
import android.widget.TextView;

import com.example.homework_day_07.evaluator.FlickerArgbEvaluator;
import com.example.homework_day_07.evaluator.JumpArgbEvaluator;
import com.example.homework_day_07.interpolator.ChargeInterpolator;
import com.example.homework_day_07.interpolator.QuadraticOvershootInterpolator;

public class HomeworkPropertyAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_property_animation);

        TextView textView = findViewById(R.id.rectangle_color_block);
        AnimatorSet rotateTranslateAnimatorSet = loadRotateTranslateAnimatorSet(textView);
        AnimatorSet alphaScaleAnimatorSet = laodAlphaScaleAnimatorSet(textView);
        AnimatorSet fullAnimatorSet = new AnimatorSet();
        fullAnimatorSet.setStartDelay(1000);
        fullAnimatorSet.play(alphaScaleAnimatorSet).after(rotateTranslateAnimatorSet);
        fullAnimatorSet.start();
    }

    ObjectAnimator loadRotateAnimator(TextView textView) {
        ObjectAnimator rotateAnimator =
                ObjectAnimator.ofFloat(
                        textView, View.ROTATION_X, 0f, 360f
                );
        BaseInterpolator rotateInterpolator = new ChargeInterpolator();
        rotateAnimator.setInterpolator(rotateInterpolator);
        return rotateAnimator;
    }

    ObjectAnimator loadBackgroundColorAnimator(TextView textView) {
        ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofInt(
                textView, "backgroundColor",
                Color.parseColor("#009688"),
                Color.RED
        );

        backgroundColorAnimator.setEvaluator(new FlickerArgbEvaluator(10, 150));
        return backgroundColorAnimator;
    }

    ObjectAnimator getTranslateAnimator(TextView textView) {
        ObjectAnimator translateAnimator =
                ObjectAnimator.ofFloat(
                        textView, View.TRANSLATION_X, 0f, 150f
                );
        BaseInterpolator translateInterpolator = new QuadraticOvershootInterpolator(0.6f, 0.8f);
        translateAnimator.setInterpolator(translateInterpolator);
        return translateAnimator;
    }

    AnimatorSet loadRotateTranslateAnimatorSet(TextView textView) {
        AnimatorSet rotateTranslateAnimatorSet = new AnimatorSet();
        ObjectAnimator rotateAnimator = loadRotateAnimator(textView);
        ObjectAnimator backgroundColorAnimator = loadBackgroundColorAnimator(textView);
        ObjectAnimator translateAnimator = getTranslateAnimator(textView);
        rotateTranslateAnimatorSet.playTogether(rotateAnimator, translateAnimator, backgroundColorAnimator);
        rotateTranslateAnimatorSet.setDuration(2000);
        return rotateTranslateAnimatorSet;
    }

    ObjectAnimator getAlphaAnimator(TextView textView) {
        return ObjectAnimator.ofFloat(
                textView, View.ALPHA, 1f, 0.5f
        );
    }

    ObjectAnimator loadColorAnimator(TextView textView) {
        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(
                textView, "textColor",
                Color.parseColor("#000000"),
                Color.BLUE
        );
        colorAnimator.setEvaluator(new JumpArgbEvaluator());
        return colorAnimator;
    }

    AnimatorSet laodAlphaScaleAnimatorSet(TextView textView) {

        ObjectAnimator alphaAnimator = getAlphaAnimator(textView);

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(
                textView, View.SCALE_X, 1f, 1.2f
        );

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(
                textView, View.SCALE_Y, 1f, 0.8f
        );

        ObjectAnimator colorAnimator = loadColorAnimator(textView);

        AnimatorSet alphaScaleAnimatorSet = new AnimatorSet();
        alphaScaleAnimatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator, colorAnimator);

        alphaScaleAnimatorSet.setDuration(1000);
        return alphaScaleAnimatorSet;
    }
}
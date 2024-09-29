package com.example.homework_day_07;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AnimatorPropertyHolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_property_holder);

        TextView textView = findViewById(R.id.component_text);
        load_dynamic_set(textView);
    }

    private void load_dynamic_holder(TextView textView) {
        PropertyValuesHolder rotateXHolder =
                PropertyValuesHolder.ofFloat(
                        View.ROTATION_X, 0f, 360f
                );
        PropertyValuesHolder translateXHolder =
                PropertyValuesHolder.ofFloat(
                        View.TRANSLATION_X, 0f, 200f
                );
        ObjectAnimator objectAnimator =
                ObjectAnimator.ofPropertyValuesHolder(
                        textView, rotateXHolder, translateXHolder
                );
        objectAnimator.setDuration(1000);
        objectAnimator.start();
    }

    private void load_dynamic_set(TextView textView) {
        ObjectAnimator rotateAnimator =
                ObjectAnimator.ofFloat(
                        textView, View.ROTATION_X, 0f, 360f
                );
        ObjectAnimator translateAnimator =
                ObjectAnimator.ofFloat(
                        textView, View.TRANSLATION_X, 0f, 300f
                );
        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(rotateAnimator, translateAnimator);
        animatorSet.play(rotateAnimator).after(translateAnimator);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    private void load_static(TextView textView) {
        AnimatorSet animatorSet =
                (AnimatorSet) AnimatorInflater.loadAnimator(
                        this, R.animator.animator_component_rotate_translate);
        animatorSet.setTarget(textView);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }
}
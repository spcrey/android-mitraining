package com.example.homework_day_07;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class AnimTranslateActivity extends AppCompatActivity {

    private static final String TAG = "AnimTranslateActivity";

    private Animation load_static() {
        return AnimationUtils.loadAnimation(this, R.anim.anim_translate);
    }

    private Animation load_dynamic() {
        TranslateAnimation translateAnimation = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_translate);
        ImageView imageView  = findViewById(R.id.translate_robot);
        Animation animation = load_dynamic();
        imageView.startAnimation(animation);
    }
}
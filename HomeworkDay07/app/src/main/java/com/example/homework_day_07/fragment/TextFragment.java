package com.example.homework_day_07.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.ImageView;

import com.example.homework_day_07.R;

public class TextFragment extends Fragment {
    private static final String TAG = "TextFragment";

    public TextFragment() {
        super(R.layout.fragment_test);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void load_static_animation_run(View view) {
        ImageView runAnimationImageView = view.findViewById(R.id.test_animation_static);
        runAnimationImageView.setImageResource(R.drawable.animation_run);
        AnimationDrawable animationDrawable = (AnimationDrawable) runAnimationImageView.getDrawable();
        animationDrawable.start();
    }

    private void load_dynamic_animation_run(View view) {
        ImageView runAnimationImageView = view.findViewById(R.id.test_animation_dynamic);
        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.setOneShot(false);
        animationDrawable.addFrame(ContextCompat.getDrawable(requireContext(), R.drawable.run1), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(requireContext(), R.drawable.run2), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(requireContext(), R.drawable.run3), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(requireContext(), R.drawable.run4), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(requireContext(), R.drawable.run5), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(requireContext(), R.drawable.run6), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(requireContext(), R.drawable.run7), 100);
        runAnimationImageView.setImageDrawable(animationDrawable);
        animationDrawable.start();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        load_static_animation_run(view);
        load_dynamic_animation_run(view);
    }
}
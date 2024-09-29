package com.example.home_workday_05.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;

import com.example.home_workday_05.R;

public class RelativeLayoutFragment extends Fragment {

    private static final String TAG = "RelativeLayoutFragment";

    public RelativeLayoutFragment() {
        super(R.layout.fragment_relative_layout);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
    }
}
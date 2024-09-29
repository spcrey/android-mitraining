package com.example.home_workday_05.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.home_workday_05.R;

public class LinearLayoutFragment extends Fragment {

    private static final String TAG = "LinearLayoutFragment";

    public LinearLayoutFragment() {
        super(R.layout.fragment_linear_layout);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
//        getActivity().set
    }
}
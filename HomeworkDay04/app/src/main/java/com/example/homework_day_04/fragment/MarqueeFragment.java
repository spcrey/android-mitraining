package com.example.homework_day_04.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.homework_day_04.R;

public class MarqueeFragment extends Fragment {

    private static final String TAG = "MarqueeFragment";

    public MarqueeFragment() {
        super(R.layout.fragment_marquee);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView textView = view.findViewById(R.id.marquee_text);
        textView.requestFocus();
        Log.d(TAG, "onViewCreated");
    }
}
package com.example.homework_day_03.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.homework_day_03.R;


public class PublicBottomFragment extends Fragment {
    private TextView tvMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_public_bottom, container, false);
        tvMessage = view.findViewById(R.id.message);
        return view;
    }

    public void setMessage(String message) {
        tvMessage.setText(message);
    }
}
package com.example.homework_day_03.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.homework_day_03.R;


public class ObjectFragment extends Fragment {

    public static final String ARG_OBJECT = "ARG_OBJECT";

    private static final String TAG = "DynamicFragment";

    public ObjectFragment() {
        super(R.layout.fragment_object);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int tem_num = requireArguments().getInt(ARG_OBJECT);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.append("_" + String.valueOf(tem_num));
        Log.d(TAG, ARG_OBJECT + ": " + String.valueOf(tem_num));
    }
}
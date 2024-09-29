package com.example.homework_day_06.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.homework_day_06.R;
import com.example.mylibrary.MyLibrary;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";

    public MainFragment() {
        super(R.layout.fragment_main);
        MyLibrary myLibrary;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView imageView = view.findViewById(R.id.image_view);
        String url = "https://ffid.oss-cn-beijing.aliyuncs.com/093ec9b8-a055-4cf3-97fe-cd1174122664.jpg";
        Glide.with(this)
                .load(url)
                .into(imageView);

        super.onViewCreated(view, savedInstanceState);
    }
}
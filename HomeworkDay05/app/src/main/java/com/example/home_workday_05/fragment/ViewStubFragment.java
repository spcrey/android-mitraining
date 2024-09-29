package com.example.home_workday_05.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.example.home_workday_05.R;

public class ViewStubFragment extends Fragment {
    private static final String TAG = "ViewStubFragment";

    public ViewStubFragment() {
        super(R.layout.fragment_view_stub);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
    }

}
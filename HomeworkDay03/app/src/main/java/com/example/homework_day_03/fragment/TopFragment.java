package com.example.homework_day_03.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.homework_day_03.R;

public class TopFragment extends Fragment {
    public interface TopPageActionListener {
        void onTopPageAction();
    }

    private TopPageActionListener topPageActionListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        view.findViewById(R.id.btn_to_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topPageActionListener != null) {
                    topPageActionListener.onTopPageAction();
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TopPageActionListener) {
            topPageActionListener = (TopPageActionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        topPageActionListener = null;
    }
}

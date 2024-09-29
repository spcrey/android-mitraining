package com.example.homework_day_10.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.homework_day_10.R;

import java.util.ArrayList;
import java.util.List;

public class TestFragment extends Fragment {
    public TestFragment() {
        super(R.layout.fragment_test);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addButton(view);
    }

    private void addButton(View view) {
        Button button = new Button(getContext());
        LinearLayout layout = view.findViewById(R.id.layout_fragment_test);
        button.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.button_height))
        );
        button.setText("dynamic button");
        button.setBackgroundResource(R.drawable.rounded_button_bg);
        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams)
                        button.getLayoutParams();
        int margin = getResources().getDimensionPixelSize(R.dimen.margin);
        params.setMargins(margin, margin, margin, margin);
        button.setLayoutParams(params);
        layout.addView(button);

        View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "dynamic button", Toast.LENGTH_SHORT).show();
            }
        };

        button.setOnClickListener(buttonOnClickListener);
        button = null;
        System.gc();
    }
}
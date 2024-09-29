package com.example.homework_day_04.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homework_day_04.R;
import com.example.homework_day_04.RadioGroupActivity;
import com.example.homework_day_04.TextCheckActivity;

public class MultiViewFragment extends Fragment implements
        CompoundButton.OnCheckedChangeListener,
        View.OnClickListener,
        View.OnLongClickListener,
        View.OnTouchListener {

    private static final String TAG = "MarqueeFragment";

    public MultiViewFragment() {
        super(R.layout.fragment_multi_view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");

        RadioGroup radioGroup = view.findViewById(R.id.btn_radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = view.findViewById(checkedId);
                Toast.makeText(getActivity(), "select sex: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        CheckBox checkBox = view.findViewById(R.id.select_apple);
        checkBox.setOnCheckedChangeListener(this);
        checkBox = view.findViewById(R.id.select_banana);
        checkBox.setOnCheckedChangeListener(this);

        Button button;
        button = view.findViewById(R.id.btn_click);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.btn_long_click);
        button.setOnLongClickListener(this);
        button = view.findViewById(R.id.btn_touch);
        button.setOnTouchListener(this);

        SeekBar seekBar = view.findViewById(R.id.image_seekbar);
        ImageView imageView = view.findViewById(R.id.image);
        TextView textView = view.findViewById(R.id.image_seekbar_info);
        imageView.setAlpha(0.0f);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                imageView.setPivotX(imageView.getWidth() / 2);
                imageView.setPivotY(imageView.getHeight() / 2);
                float alpha = progress / 100.0f;
                int rotation = (int)(progress * 3.60);
                imageView.setRotation(rotation);
                imageView.setAlpha(alpha);
                textView.setText("rotate=" + rotation + ", alpha=" + alpha);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        Button button_summit = view.findViewById(R.id.btn_summit);
        EditText editText = view.findViewById(R.id.edit_text);
        button_summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getText();
                Toast.makeText(getActivity(), editText.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        button_summit.setEnabled(false);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    button_summit.setEnabled(false);
                }
                else {
                    button_summit.setEnabled(true);
                    char c = s.charAt(s.length()-1);
                    Log.d(TAG, String.valueOf(c));
                    if(c >= 'A' && c <= 'Z')
                        return;
                    Toast.makeText(getActivity(), "del: " + c, Toast.LENGTH_SHORT).show();
                    s = s.subSequence(0, s.length()-1);
                    editText.setText(s);
                    editText.setSelection(s.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Toast.makeText(getActivity(), "select fruit: " + buttonView.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_click) {
            Toast.makeText(getActivity(), "onClick", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.btn_long_click) {
            Toast.makeText(getActivity(), "onLongClick", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.btn_touch) {
            Toast.makeText(getActivity(), "onTouch", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
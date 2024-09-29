package com.example.homework_day_04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TextCheckActivity extends AppCompatActivity
        implements View.OnClickListener, TextWatcher {

    private static final String TAG = "TextCheckActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_check);

        Button button = findViewById(R.id.btn_summit);
        button.setOnClickListener(this);
        button.setEnabled(false);

        EditText editText = findViewById(R.id.edit_text);
        editText.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_summit) {
            EditText editText = findViewById(R.id.edit_text);
            editText.getText();
            Toast.makeText(TextCheckActivity.this, editText.getText(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Button button = findViewById(R.id.btn_summit);
        EditText editText = findViewById(R.id.edit_text);
        if (TextUtils.isEmpty(s)) {
            button.setEnabled(false);
        }
        else {
            button.setEnabled(true);
            char c = s.charAt(s.length()-1);
            Log.d(TAG, String.valueOf(c));
            if(c >= 'A' && c <= 'C')
                return;
            if(c >= 'a' && c <= 'c')
                return;
            if(c >= '1' && c <= '3')
                return;
            Toast.makeText(TextCheckActivity.this, "del: " + c, Toast.LENGTH_SHORT).show();
            s = s.subSequence(0, s.length()-1);
            editText.setText(s);
            editText.setSelection(s.length());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
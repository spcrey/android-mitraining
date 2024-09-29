package com.example.homework_day_04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekbarImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbar_image);

        SeekBar seekBar = findViewById(R.id.image_seekbar);
        ImageView imageView = findViewById(R.id.image);
        TextView textView = findViewById(R.id.image_seekbar_info);
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
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
package com.example.homework_day_04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SpanTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span_text);

        TextView textView = findViewById(R.id.span_text);
        String text = "this is text include red background, green text and clicked text which color is green and background is blue";
        SpannableString spannableString = new SpannableString(text);

        spannableString.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.red)), 21, 35, Spannable.SPAN_COMPOSING);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green)), 37, 47, Spannable.SPAN_COMPOSING);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(SpanTextActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        }, 52, 108, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        Drawable drawable = getResources().getDrawable(R.drawable.orange);
        drawable.setBounds(0, 0, 80, 80);
        spannableString.setSpan(new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }


}
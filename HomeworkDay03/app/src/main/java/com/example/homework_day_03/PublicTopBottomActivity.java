package com.example.homework_day_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.homework_day_03.fragment.TopFragment;
import com.example.homework_day_03.fragment.PublicBottomFragment;

public class PublicTopBottomActivity extends AppCompatActivity implements TopFragment.TopPageActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_top_bottom);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.top_bottom_fragment_container_view, TopFragment.class, null, "BUTTON_FRAGMENT_TAG")
                .commit();
    }

    @Override
    public void onTopPageAction() {
        PublicBottomFragment fragment = new PublicBottomFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.top_bottom_fragment_container_view, fragment)
                .commitNow();
        fragment.setMessage("this is a message from top fragment");
    }
}

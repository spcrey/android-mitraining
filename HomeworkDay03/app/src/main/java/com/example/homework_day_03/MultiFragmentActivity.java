package com.example.homework_day_03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.homework_day_03.fragment.DynamicFragment;
import com.example.homework_day_03.fragment.ReplaceFragment;

public class MultiFragmentActivity extends AppCompatActivity {

    private static final String TAG = "MultiFragmentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_fragment);
    }

    public void addFragment(View v) {
        Log.d(TAG, "addFragment");

        Bundle bundle = new Bundle();
        bundle.putInt("tem_num", 82513);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.dynamic_fragment_container_view, DynamicFragment.class, bundle, "BUTTON_FRAGMENT_TAG")
                .commit();
    }

    public void removeFragment(View v) {
        Log.d(TAG, "removeFragment");
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("BUTTON_FRAGMENT_TAG");
        assert fragment != null;
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .remove(fragment)
                .commit();
    }

    public void replaceFragment(View v) {
        Log.d(TAG, "replaceFragment");
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.slide_out,
                        R.anim.slide_in,
                        R.anim.slide_out
                )
                .setReorderingAllowed(true)
                .replace(R.id.dynamic_fragment_container_view, ReplaceFragment.class, null, "BUTTON_FRAGMENT_TAG")
                .commit();
    }

    public void hideFragment(View v) {
        Log.d(TAG, "hideFragment");
        Fragment demoFragment = getSupportFragmentManager().findFragmentByTag("BUTTON_FRAGMENT_TAG");
        assert demoFragment != null;
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .hide(demoFragment)
                .commit();
    }

    public void showFragment(View v) {
        Log.d(TAG, "showFragment");
        Fragment demoFragment = getSupportFragmentManager().findFragmentByTag("BUTTON_FRAGMENT_TAG");
        assert demoFragment != null;
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .show(demoFragment)
                .commit();
    }

    public void backLastStep(View v) {
    }
}

package com.example.homework_day_02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
    }

    public void toSubActivity(View view) {
        Log.d(TAG, "toSubActivity1");
        Intent intent = new Intent(this, SubActivity1.class);
        startActivity(intent);
    }

    public void toImplicitIntentActivity(View view) {
        Log.d(TAG, "toImplicitIntentActivity");
        Intent intent = new Intent(this, ImplicitIntentActivity.class);
        startActivity(intent);
    }

    public void toServiceActivity(View view) {
        Log.d(TAG, "toServiceActivity");
        Intent intent = new Intent(this, ServiceActivity.class);
        startActivity(intent);
    }

    public void toServiceBindActivity(View view) {
        Log.d(TAG, "toServiceBindActivity");
        Intent intent = new Intent(this, ServiceBindActivity.class);
        startActivity(intent);
    }

    public void toCalculatorServiceActivity(View view) {
        Log.d(TAG, "toCalculatorServiceActivity");
        Intent intent = new Intent(this, AidlCalculatorActivity.class);
        startActivity(intent);
    }

    public void toNetworkReceiverActivity(View view) {
        Log.d(TAG, "toNetworkReceiverActivity");
        Intent intent = new Intent(this, DynamicReceiverActivity.class);
        startActivity(intent);
    }

    public void toContactsActivity(View view) {
        Log.d(TAG, "toContactsActivity");
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}

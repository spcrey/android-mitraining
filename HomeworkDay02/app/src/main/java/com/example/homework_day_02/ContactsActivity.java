package com.example.homework_day_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.Manifest;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private static final String TAG = "ContactsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Button readBtn = findViewById(R.id.btn_read);
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ContactsActivity.this,
                        Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    ActivityCompat.requestPermissions(ContactsActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS}, 123);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requsetCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requsetCode, permissions, grantResults);
        if (requsetCode == 123) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                readContacts();
            }
        }
    }

    private void readContacts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> contacts = getContactsList();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView contactsTv = findViewById(R.id.tv_contacts);
                        StringBuilder sb = new StringBuilder();
                        for (String contact: contacts) {
                            sb.append(contact).append("\n");
                        }
                        contactsTv.setText(sb.toString());
                    }
                });
            }
        }).start();
    }

    private List<String> getContactsList() {
        List<String> list = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        String[] projections = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
        };
        try (Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                projections,
                null,
                null,
                null
        )) {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String contactId = cursor.getString(
                            cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID)
                    );
                    String contactName = cursor.getString(
                            cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)
                    );
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone._ID + "= ?",
                            new String[]{contactId},
                            null
                    );
                    if (phoneCursor != null && phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(
                                phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        );
                        Log.d(TAG, "name: " + contactName + ", phone: " + phoneNumber);
                        list.add("name: " + contactName + ", phone: " + phoneNumber);
                    }

                    if (phoneCursor != null) {
                        phoneCursor.close();
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "error", e);
        }
        return list;
    }
}
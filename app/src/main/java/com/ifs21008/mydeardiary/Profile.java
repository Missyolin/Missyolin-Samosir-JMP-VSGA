package com.ifs21008.mydeardiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new DatabaseHelper(this);
        String email = dbHelper.getEmail();

        TextView tvUsername = findViewById(R.id.tvUsername);
        tvUsername.setText(email);
    }
}
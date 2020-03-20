package com.tripper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        setContentView(R.layout.activity_login_page);
    }
}

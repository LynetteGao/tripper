package com.tripper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        setContentView(R.layout.activity_login_page);


    }
    public void logIn(View view) {
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);
    }
    public void signUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}

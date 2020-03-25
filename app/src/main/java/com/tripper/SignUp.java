package com.tripper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        setContentView(R.layout.activity_sign_up);
    }
    public void register(View view) {
        Intent intent = new Intent(this, HomePage.class);
        EditText displayName = findViewById(R.id.displayName);
        intent.putExtra("name", displayName.getText().toString());
        startActivity(intent);
    }
}

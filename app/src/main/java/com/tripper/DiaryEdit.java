package com.tripper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DiaryEdit extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView month, day, timeOfDay, events;
        String m, d, t, concatenated = "";
        ArrayList<String> e;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);

        Intent intent = getIntent();
        m = intent.getStringExtra("month");
        d = intent.getStringExtra("day");
        t = intent.getStringExtra("timeOfDay");
        e = intent.getStringArrayListExtra("events");

        for(String event : e){
            concatenated += "@" + event + "\n";
        }

        month = findViewById(R.id.month);
        day = findViewById(R.id.day);
        timeOfDay = findViewById(R.id.timeOfDay);
        events = findViewById(R.id.events);

        month.setText(m);
        day.setText(d);
        timeOfDay.setText(t);
        events.setText(concatenated);
    }
}

package com.tripper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.tripper.db.entities.Diary;
import com.tripper.db.entities.DiaryEntry;
import com.tripper.viewmodels.DiaryEditViewModel;

import java.util.ArrayList;
import java.util.List;

public class DiaryEdit extends AppCompatActivity implements View.OnClickListener{
    DiaryEditViewModel diaryEditViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView month, day, timeOfDay, events, diaryText;
        String m, d, t, concatenated = "", textHolder;
        ArrayList<String> e;
        long diaryId;
        Button save;

        diaryEditViewModel = new ViewModelProvider(this).get(DiaryEditViewModel.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);

        Intent intent = getIntent();
        m = intent.getStringExtra("month");
        d = intent.getStringExtra("day");
        t = intent.getStringExtra("timeOfDay");
        e = intent.getStringArrayListExtra("events");
        diaryId = intent.getLongExtra("diaryId", 123);

        for(String event : e){
            concatenated += "@" + event + "\n";
        }

        month = findViewById(R.id.month);
        day = findViewById(R.id.day);
        timeOfDay = findViewById(R.id.timeOfDay);
        events = findViewById(R.id.events);
        diaryText = findViewById(R.id.diary);
        save = findViewById(R.id.save);

        month.setText(m);
        day.setText(d);
        timeOfDay.setText(t);
        events.setText(concatenated);
        save.setOnClickListener(this);

        textHolder = diaryEditViewModel.getDiaryEntryById(diaryId).diaryText;

        if(!textHolder.equals("none")){
            diaryText.setText(textHolder);
        }
    }

    @Override
    public void onClick(View view) {
        TextView diaryText = findViewById(R.id.diary);
        long diaryId, Id, tripId;
        DiaryEntry diaryEntry = new DiaryEntry();

        Intent intent = getIntent();
        diaryId = intent.getLongExtra("diaryId", 123);

        Id = diaryEditViewModel.getDiaryEntryById(diaryId).id;

        diaryEntry.id = Id;
        diaryEntry.diaryId = diaryId;
        diaryEntry.diaryText = diaryText.getText().toString();

        diaryEditViewModel.updateDiaryEntry(diaryEntry);

        tripId = intent.getLongExtra("tripId", 123);

        Intent goToTimeLine = new Intent(this, TripOverview.class);
        goToTimeLine.putExtra("tripId", tripId);
        goToTimeLine.putExtra("fragment_id", 2);
        startActivity(goToTimeLine);
    }
}

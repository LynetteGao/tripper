package com.tripper;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LocationSuggestion extends AppCompatActivity {
    Context context;
    //String[] data;
    int[] locImages = {R.drawable.brittinghamboats, R.drawable.madisonmccall, R.drawable.bouldersclimbing};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_suggestion);
        RecyclerView locationList = findViewById(R.id.recycler_view);
        locationList.setLayoutManager(new LinearLayoutManager(this));
        String[] items = getResources().getStringArray(R.array.locationList);
        locationList.setAdapter(new LocationAdapter(context, items, locImages));
    }
}


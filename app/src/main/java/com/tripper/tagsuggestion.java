package com.tripper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

public class tagsuggestion extends AppCompatActivity {
    Context context;
    //String[] data;
    int[] images = {R.drawable.sightseeing, R.drawable.art, R.drawable.sport, R.drawable.history,
    R.drawable.leisure, R.drawable.eateries};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagsuggestion);
        RecyclerView tagList = findViewById(R.id.recycler_view);
        tagList.setLayoutManager(new LinearLayoutManager(this));
        String[] items = getResources().getStringArray(R.array.tagList);
        //String[] items = {"Sightseeing","Art","History","Sport"};
        tagList.setAdapter(new TagAdapter(context, items, images));
    }
}

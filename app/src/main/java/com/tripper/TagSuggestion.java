package com.tripper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;


public class TagSuggestion extends AppCompatActivity {
    private ArrayList<TagItem> tagItemArrayList;
    private RecyclerView mRecyclerView;
    private TagAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Button select;
    int[] images = {R.drawable.sightseeing, R.drawable.art, R.drawable.sport, R.drawable.history,
            R.drawable.leisure, R.drawable.eateries};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagsuggestion);
        createTagList();
        buildRecyclerView();
        select = (Button) findViewById(R.id.selectedTagBtn);
        select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (int i = 0; i < tagItemArrayList.size(); i++) {
                    if (tagItemArrayList.get(i).isSelected()) {
                        Log.d("Selected TAG: ", tagItemArrayList.get(i).getTagText());
                    }
                }
            }
        });
    }

    public void createTagList() {
        tagItemArrayList = new ArrayList<>();
        tagItemArrayList.add(new TagItem(R.drawable.sightseeing, "Sightseeing"));
        tagItemArrayList.add(new TagItem(R.drawable.art, "Art"));
        tagItemArrayList.add(new TagItem(R.drawable.sport, "Sport"));
        tagItemArrayList.add(new TagItem(R.drawable.history, "History"));
        tagItemArrayList.add(new TagItem(R.drawable.leisure, "Leisure"));
        tagItemArrayList.add(new TagItem(R.drawable.eateries, "Eateries"));
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new TagAdapter(tagItemArrayList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}

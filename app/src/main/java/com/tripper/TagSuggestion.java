package com.tripper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tripper.db.entities.Tag;
import com.tripper.db.entities.Trip;
import com.tripper.db.entities.TripTagCrossRef;
import com.tripper.viewmodels.TagSuggestionViewModel;

import java.util.ArrayList;
import java.util.List;


public class TagSuggestion extends AppCompatActivity {
    private ArrayList<TagItem> tagItemArrayList;
    private RecyclerView mRecyclerView;
    private TagAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TagSuggestionViewModel tagSuggestionViewModel;
    private List<Tag> defaultTags;
    private Long tripId;
    private Long segId;

    Button select;
    int[] images = {R.drawable.sightseeing, R.drawable.art, R.drawable.sport, R.drawable.history,
            R.drawable.leisure, R.drawable.eateries};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagsuggestion);
        tagSuggestionViewModel = new ViewModelProvider(this).get(TagSuggestionViewModel.class);
        Intent intent = getIntent();
        tripId = intent.getLongExtra("tripId", -1);
        segId = intent.getLongExtra("segId",-1);
        defaultTags = tagSuggestionViewModel.getDefaultTags();
        createTagList();
        buildRecyclerView();


        select = (Button) findViewById(R.id.selectedTagBtn);
        select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<TripTagCrossRef> tagCrossRefs = new ArrayList<>();
                for (int i = 0; i < tagItemArrayList.size(); i++) {
                    if (tagItemArrayList.get(i).isSelected()) {
                        Log.d("Selected TAG: ", tagItemArrayList.get(i).getTag().name);
                        tagCrossRefs.add(new TripTagCrossRef(tagItemArrayList.get(i).getTag().id, tripId));
                    }
                }

                tagSuggestionViewModel.insertTripTags(tagCrossRefs);

                Intent intent = new Intent(v.getContext(), LocationSuggestion.class);
                intent.putExtra("tripId", tripId);
                intent.putExtra("segId", segId);
                startActivity(intent);

            }
        });
    }

    public void createTagList() {
        tagItemArrayList = new ArrayList<>();
        for (Tag tag : defaultTags) {
            tagItemArrayList.add(new TagItem(tag, this.getResources().getIdentifier(
                    tag.icon, "drawable", this.getPackageName())));
        }

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

package com.tripper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

public class LocationSuggestion extends AppCompatActivity {
    private ArrayList<LocationItem> locationItemArrayList;
    private RecyclerView mRecyclerView;
    private LocationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Button select;
    int[] locImages = {R.drawable.brittinghamboats, R.drawable.madisonmccall, R.drawable.bouldersclimbing};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_suggestion);
        createLocationList();
        buildRecyclerView();
        select = (Button) findViewById(R.id.selectedLocBtn);
        select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (int i = 0; i < locationItemArrayList.size(); i++) {
                    if (locationItemArrayList.get(i).isSelected()) {
                        Log.d("Selected Location: ", locationItemArrayList.get(i).getLocationText());
                    }
                }
            }
        });
    }

    public void createLocationList() {
        locationItemArrayList = new ArrayList<>();
        locationItemArrayList.add(new LocationItem(R.drawable.brittinghamboats, "Brittingham Boats"));
        locationItemArrayList.add(new LocationItem(R.drawable.madisonmccall, "Madison McCall"));
        locationItemArrayList.add(new LocationItem(R.drawable.bouldersclimbing, "Boulders Climbing"));
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new LocationAdapter(locationItemArrayList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}



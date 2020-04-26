package com.tripper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;

public class LocationSuggestion extends AppCompatActivity {
    private ArrayList<LocationItem> locationItemArrayList;
    private RecyclerView mRecyclerView;
    private LocationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int tripId;
    private AutocompleteSupportFragment autocompleteSupportFragment;
    Button select;
    int[] locImages = {R.drawable.brittinghamboats, R.drawable.madisonmccall, R.drawable.bouldersclimbing};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_suggestion);
        Intent intent = getIntent();
        tripId = intent.getIntExtra("tripId", -1);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.places_api_key));
        }

        autocompleteSupportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.locSuggestionAutoComplete);
        autocompleteSupportFragment.setHint("Search");

        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.d("place", place.getName());
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

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



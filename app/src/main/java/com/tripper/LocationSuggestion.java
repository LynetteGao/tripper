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
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.tripper.db.entities.Tag;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;
import com.tripper.db.relationships.TripWithTags;
import com.tripper.viewmodels.LocationSuggestionViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LocationSuggestion extends AppCompatActivity {
    private ArrayList<LocationItem> locationItemArrayList;
    private RecyclerView mRecyclerView;
    private LocationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LocationSuggestionViewModel locationSuggestionViewModel;
    private Long tripId;
    private AutocompleteSupportFragment autocompleteSupportFragment;
    Button select;

    int[] locImages = {R.drawable.brittinghamboats, R.drawable.madisonmccall, R.drawable.bouldersclimbing};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_suggestion);
        locationSuggestionViewModel = new LocationSuggestionViewModel(getApplication());
        Intent intent = getIntent();
        tripId = intent.getLongExtra("tripId", -1);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.places_api_key));
        }

        TripWithTags tripWithTags = locationSuggestionViewModel.getTripWithTags(tripId);

        autocompleteSupportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.locSuggestionAutoComplete);
        autocompleteSupportFragment.setHint("Search");

        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.d("place", Objects.requireNonNull(place.getName()));
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



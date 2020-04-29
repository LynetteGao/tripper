package com.tripper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.PlacesApi;
import com.google.maps.android.SphericalUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.RankBy;
import com.tripper.db.entities.Event;
import com.tripper.db.entities.Tag;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;
import com.tripper.db.relationships.TripWithTags;
import com.tripper.viewmodels.LocationSuggestionViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Retrofit;

public class LocationSuggestion extends AppCompatActivity {
    private ArrayList<LocationItem> locationItemArrayList;
    private RecyclerView mRecyclerView;
    private LocationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LocationSuggestionViewModel locationSuggestionViewModel;
    private Long tripId;
    private AutocompleteSupportFragment autocompleteSupportFragment;
    private List<PlacesSearchResult> searchResults = new ArrayList<>();
    private List<LocationItem> locationItems = new ArrayList<>();
    private TripWithDaysAndDaySegments tripWithDaysAndDaySegments;
    Button select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_suggestion);
        locationSuggestionViewModel = new LocationSuggestionViewModel(getApplication());
        Intent intent = getIntent();
        tripId = intent.getLongExtra("tripId", -1);
        tripWithDaysAndDaySegments = locationSuggestionViewModel.getTripWithDaysAndDaySegments(tripId);

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
                Log.d("place", Objects.requireNonNull(place.getName()));
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

        //createLocationList();
        buildRecyclerView();
        select = (Button) findViewById(R.id.selectedLocBtn);
        select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (LocationItem item : locationItems) {
                    if (item.isSelected()) {
                        PlacesSearchResult result = item.getPlacesSearchResult();
                        Log.d("Selected Location: ", item.getPlacesSearchResult().name);
                        Event event = new Event();
                        event.name = result.name;
                        event.locationLon = Double.toString(result.geometry.location.lng);
                        event.locationLat = Double.toString(result.geometry.location.lat);
                        event.tripId = tripWithDaysAndDaySegments.trip.id;
                        event.segmentId = tripWithDaysAndDaySegments.days.get(0).daySegments.get(0).daySegment.id;
                        locationSuggestionViewModel.insertEvent(event);
                        Intent intent = new Intent(getApplicationContext(), TripOverview.class);
                        intent.putExtra("tripId", tripWithDaysAndDaySegments.trip.id);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    // call places api here to get suggestions
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        TripWithTags trip = locationSuggestionViewModel.getTripWithTags(tripId);


        mAdapter = new LocationAdapter(getApplicationContext(), locationItems, locationSuggestionViewModel);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        List<Tag> tags = trip.tags;
        com.google.maps.model.LatLng center = new com.google.maps.model.LatLng(Double.parseDouble(trip.trip.locationLat),
                Double.parseDouble(trip.trip.locationLon));
        for (Tag tag: tags) {
            getPlacesSearch(center, tag.name);
        }
    }

    private void getPlacesSearch(com.google.maps.model.LatLng center, String query) {
        PlacesSearchResponse resp = new PlacesSearchResponse();
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(getString(R.string.maps_api_key))
                .build();

        PlacesApi.nearbySearchQuery(context, center)
                .radius(5000)
                .rankby(RankBy.PROMINENCE)
                .keyword(query)
                .setCallback(new PendingResult.Callback<PlacesSearchResponse>() {
                    @Override
                    public void onResult(PlacesSearchResponse result) {
                        searchResults.addAll(Arrays.asList(result.results));
                        for (PlacesSearchResult res : result.results) {
                            locationItems.add(new LocationItem(res));
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        e.printStackTrace();
                    }
                });

    }
}



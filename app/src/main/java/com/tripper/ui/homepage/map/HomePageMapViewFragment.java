package com.tripper.ui.homepage.map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tripper.R;
import com.tripper.TripOverview;
import com.tripper.db.entities.Trip;

import java.util.List;

public class HomePageMapViewFragment extends Fragment {

    private HomePageMapViewModel mapViewModel;
    private GoogleMap gMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel =
                new ViewModelProvider(this).get(HomePageMapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_homepage_map_view, container, false);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.homeMapFrag);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                // clicking the info window should take you to trip overview
                gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Integer tripId = (Integer) marker.getTag();
                        Log.i("map marker", tripId.toString());
                        Intent intent = new Intent(getContext(), TripOverview.class);
                        intent.putExtra("tripId", tripId);
                        startActivity(intent);
                    }
                });

            }
        });

        mapViewModel.getTrips().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                if (!trips.isEmpty()) {
                    for (Trip trip : trips) {
                        // create a map marker for each trip
                        // TODO: different colors for upcoming, current, and past trips
                        LatLng latLng = new LatLng(Double.parseDouble(trip.locationLat), Double.parseDouble(trip.locationLon));
                        gMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(trip.name))
                                .setTag(trip.id); // put the tripId in the marker so we can send it in an intent later
                    }
                }
            }
        });



        return root;
    }
}

package com.tripper.ui.homepage.map;

import android.os.Bundle;
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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tripper.R;
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
        mapFragment.getMapAsync(googleMap -> {
            gMap = googleMap;
        });

        mapViewModel.getTrips().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                if (!trips.isEmpty()) {
                    for (Trip trip : trips) {
                        LatLng latLng = new LatLng(Double.parseDouble(trip.locationLat), Double.parseDouble(trip.locationLon));
                        gMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(trip.name));
                    }
                }
            }
        });



        return root;
    }
}

package com.tripper.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.maps.android.SphericalUtil;
import com.tripper.db.entities.Tag;
import com.tripper.db.entities.Trip;
import com.tripper.db.relationships.TripWithTags;
import com.tripper.repositories.TripRepository;

import java.util.ArrayList;
import java.util.List;

public class LocationSuggestionViewModel extends AndroidViewModel {
    private TripRepository tripRepository;

    public LocationSuggestionViewModel(Application application) {
        super(application);
        tripRepository = new TripRepository(application);
    }

    public TripWithTags getTripWithTags(long tripId) {
        return tripRepository.getTripWithTags(tripId);
    }
}

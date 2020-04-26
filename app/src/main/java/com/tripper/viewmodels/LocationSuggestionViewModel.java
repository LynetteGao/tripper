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

    public List<AutocompletePrediction> getSuggestedPlaces(TripWithTags trip) {
        List<Tag> tags = trip.tags;
        List<AutocompletePrediction> predictions = new ArrayList<>();
        LatLng center = new LatLng(Double.parseDouble(trip.trip.locationLat),
                Double.parseDouble(trip.trip.locationLon));
        LatLng north = SphericalUtil.computeOffset(center, 5000, 0);
        LatLng south = SphericalUtil.computeOffset(center, 5000, 180);

        LatLngBounds bounds = LatLngBounds.builder()
                .include(north)
                .include(south)
                .build();

        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        PlacesClient placesClient = Places.createClient(getApplication().getApplicationContext());

        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setLocationBias(RectangularBounds.newInstance(bounds))
                .setOrigin(center)
                .setSessionToken(token)
                .setQuery(tags.get(0).name)
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
           for (AutocompletePrediction prediction: response.getAutocompletePredictions()) {
               Log.d("predict", prediction.getPlaceId());
               predictions.add(prediction);
            }
        });

        return predictions;
    }

    public TripWithTags getTripWithTags(long tripId) {
        return tripRepository.getTripWithTags(tripId);
    }
}

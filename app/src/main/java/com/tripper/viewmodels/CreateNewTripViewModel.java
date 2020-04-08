package com.tripper.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.tripper.db.entities.Trip;
import com.tripper.repositories.TripRepository;

public class CreateNewTripViewModel extends AndroidViewModel {
    private TripRepository tripRepository;

    public CreateNewTripViewModel(Application application) {
        super(application);
        tripRepository = new TripRepository(application);
    }

    public void insert(Trip trip) {
        tripRepository.insertTrip(trip);
    }
}

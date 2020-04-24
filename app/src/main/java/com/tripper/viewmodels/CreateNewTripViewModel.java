package com.tripper.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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

    public LiveData<Trip> getMostRecentTrip() {
        return tripRepository.getMostRecentTrip();
    }
}

package com.tripper.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tripper.db.entities.Trip;
import com.tripper.repositories.TripRepository;

import java.util.List;

public class TripViewModel extends AndroidViewModel {
    private TripRepository tripRepository;
    private LiveData<List<Trip>> trips;

    public TripViewModel(Application application) {
        super(application);
        tripRepository = new TripRepository(application);
        trips = tripRepository.getTrips();
    }

    public void insert(Trip trip) {
        tripRepository.insert(trip);
    }
}

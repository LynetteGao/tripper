package com.tripper.ui.homepage.map;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tripper.db.entities.Trip;
import com.tripper.repositories.TripRepository;

import java.util.List;

public class HomePageMapViewModel extends AndroidViewModel {

    private TripRepository tripRepository;
    private LiveData<List<Trip>> allTrips;

    public HomePageMapViewModel(Application application) {
        super(application);
        tripRepository = new TripRepository(application);
        allTrips = tripRepository.getTrips();

    }

    public LiveData<List<Trip>> getTrips() {
        return allTrips;
    }


}
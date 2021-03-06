package com.tripper.ui.homepage.list;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tripper.db.entities.Trip;
import com.tripper.repositories.TripRepository;

import java.util.List;

public class HomePageListViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private TripRepository tripRepository;
    private LiveData<List<Trip>> allTrips;

    public HomePageListViewModel(Application application) {
        super(application);
        tripRepository = new TripRepository(application);
        allTrips = tripRepository.getLiveTrips();

        mText = new MutableLiveData<>();
        mText.setValue("Placeholder for list view");
    }

    public LiveData<List<Trip>> getTrips() {
        return allTrips;
    }

    public void deleteTrip(Trip trip) { tripRepository.deleteTrip(trip); }
}
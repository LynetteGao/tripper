package com.tripper.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tripper.db.entities.Day;
import com.tripper.db.entities.DaySegment;
import com.tripper.db.entities.Trip;
import com.tripper.repositories.TripRepository;

public class CreateNewTripViewModel extends AndroidViewModel {
    private TripRepository tripRepository;

    public CreateNewTripViewModel(Application application) {
        super(application);
        tripRepository = new TripRepository(application);
    }

    public Long insertTrip(Trip trip) {
        return tripRepository.insertTrip(trip);
    }

    public Long insertDay(Day day) { return tripRepository.insertDay(day); }

    public void insertDaySegment(DaySegment daySegment) { tripRepository.insertDaySegment(daySegment); }

    public LiveData<Trip> getMostRecentTrip() {
        return tripRepository.getMostRecentTrip();
    }
}

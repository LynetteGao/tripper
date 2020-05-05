package com.tripper.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.tripper.db.entities.Day;
import com.tripper.db.entities.DaySegment;
import com.tripper.db.entities.Trip;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;
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

    public Long insertDay(Day day) { return tripRepository.insertDaySync(day); }

    public void insertDaySegment(DaySegment daySegment) { tripRepository.insertDaySegment(daySegment); }

    public TripWithDaysAndDaySegments getTrip(Long tripId) {
        return tripRepository.getTripWithDaysAndSegmentsById(tripId);
    }
}

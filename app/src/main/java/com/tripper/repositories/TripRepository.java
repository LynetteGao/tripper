package com.tripper.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.tripper.db.TripperDatabase;
import com.tripper.db.dao.TripDao;
import com.tripper.db.entities.Trip;
import com.tripper.db.entities.TripTagCrossRef;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;

import java.util.List;

public class TripRepository {
    private TripDao tripDao;
    private LiveData<List<Trip>> trips;

    public TripRepository(Application application) {
        TripperDatabase db = TripperDatabase.getDatabase(application);
        tripDao = db.tripDao();
    }

    public LiveData<List<Trip>> getLiveTrips() {
        return tripDao.getLiveTripsDesc();
    }

    public void insertTrip(Trip trip) {
        TripperDatabase.databaseWriteExecutor.execute(() -> {
            tripDao.insertTrip(trip);
        });
    }

    public TripWithDaysAndDaySegments getTripWithDaysAndSegmentsById(int tripId) {
        return tripDao.getTripWithDaysAndDaySegmentsById(tripId);
    }

    public void deleteTrip(Trip trip) {
        TripperDatabase.databaseWriteExecutor.execute(() -> {
            tripDao.deleteTrip(trip);
        });
    }

    public void insertTripTag(TripTagCrossRef tripTagCrossRef) {
        TripperDatabase.databaseWriteExecutor.execute(() -> {
            tripDao.insertTripTag(tripTagCrossRef);
        });
    }
}

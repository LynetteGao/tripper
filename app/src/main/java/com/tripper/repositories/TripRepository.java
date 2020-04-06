package com.tripper.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.tripper.db.TripperDatabase;
import com.tripper.db.dao.TripDao;
import com.tripper.db.entities.Trip;

import java.util.List;

public class TripRepository {
    private TripDao tripDao;
    private LiveData<List<Trip>> trips;

    public TripRepository(Application application) {
        TripperDatabase db = TripperDatabase.getDatabase(application);
        tripDao = db.tripDao();
        trips = tripDao.getLiveTripsDesc();
    }

    public LiveData<List<Trip>> getTrips() {
        return trips;
    }

    public void insert(Trip trip) {
        TripperDatabase.databaseWriteExecutor.execute(() -> {
            tripDao.insertTrip(trip);
        });
    }
}

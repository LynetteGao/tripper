package com.tripper.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.tripper.db.TripperDatabase;
import com.tripper.db.dao.TripDao;
import com.tripper.db.entities.Day;
import com.tripper.db.entities.DaySegment;
import com.tripper.db.entities.Tag;
import com.tripper.db.entities.Trip;
import com.tripper.db.entities.TripTagCrossRef;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

    public long insertTrip(Trip trip) {
        Callable<Long> insertCallable = () -> tripDao.insertTrip(trip);
        long ret = 0;

        Future<Long> future = TripperDatabase.databaseWriteExecutor.submit(insertCallable);
        try {
            ret = future.get();
        }
        catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        return ret;
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

    public void insertTripTags(List<TripTagCrossRef> tripTagCrossRefs) {
        TripperDatabase.databaseWriteExecutor.execute(() -> {
            tripDao.insertTripTags(tripTagCrossRefs);
        });
    }

    public LiveData<Trip> getMostRecentTrip() {
        return tripDao.getMostRecentTrip();
    }

    public List<Tag> getDefaultTags() {
           return tripDao.getTags();
    }

    public long insertDay(Day day) {
        Callable<Long> insertCallable = () -> tripDao.insertDay(day);
        long ret = 0;
        Future<Long> future = TripperDatabase.databaseWriteExecutor.submit(insertCallable);
        try {
            ret = future.get();
        }
        catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        return ret;
    }

    public void insertDaySegment(DaySegment daySegment) {
        TripperDatabase.databaseWriteExecutor.execute(() -> {
            tripDao.insertDaySegment(daySegment);
        });
    }

    private static class InsertTripTask extends AsyncTask<Trip, Void, Long> {
        private TripDao dao;

        InsertTripTask (TripDao dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(final Trip... params) {
            Long retVal = dao.insertTrip(params[0]);
            return retVal;
        }
    }
}


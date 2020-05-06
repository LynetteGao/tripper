package com.tripper.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.tripper.db.TripperDatabase;
import com.tripper.db.dao.TripDao;
import com.tripper.db.entities.Day;
import com.tripper.db.entities.DaySegment;
import com.tripper.db.entities.Diary;
import com.tripper.db.entities.DiaryEntry;
import com.tripper.db.entities.Event;
import com.tripper.db.entities.Tag;
import com.tripper.db.entities.Trip;
import com.tripper.db.entities.TripTagCrossRef;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;
import com.tripper.db.relationships.TripWithTags;

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

    public TripWithDaysAndDaySegments getTripWithDaysAndSegmentsById(Long tripId) {
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

    public long insertDaySync(Day day) {
        return insertLongCallable(() -> tripDao.insertDay(day));
    }

    public void insertDaySegment(DaySegment daySegment) {
        TripperDatabase.databaseWriteExecutor.execute(() -> {
            tripDao.insertDaySegment(daySegment);
        });
    }

    public TripWithTags getTripWithTags(long tripId) {
        return tripDao.getTripWithTags(tripId);
    }

    public void insertEvent(Event event) {
        TripperDatabase.databaseWriteExecutor.execute(() -> {
            tripDao.insertEvent(event);
        });
    }

    public long insertDiarySync(Diary diary) {
        return insertLongCallable(() -> tripDao.insertDiary(diary));
    }

    public long insertDiaryEntrySync(DiaryEntry diaryEntry) {
        return insertLongCallable(() -> tripDao.insertDiaryEntry(diaryEntry));
    }

    public void insertDiaryAsync(Diary diary) {
        TripperDatabase.databaseWriteExecutor.execute(() -> {
            tripDao.insertDiary(diary);
        });
    }

    public void insertDiaryEntryAsync(DiaryEntry diaryEntry) {
        TripperDatabase.databaseWriteExecutor.execute(() -> {
            tripDao.insertDiaryEntry(diaryEntry);
        });
    }

    public LiveData<List<DiaryEntry>> getDiaryEntriesById(long diaryId) {
        return tripDao.getDiaryEntriesById(diaryId);
    }

    // helper method for inserting into database synchronously
    private long insertLongCallable(Callable<Long> insertCallable) {
        long ret = 0;
        Future<Long> future = TripperDatabase.databaseWriteExecutor.submit(insertCallable);
        try {
            ret = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void updateTrip(Trip trip) {
        tripDao.updateTrip(trip);
    }

    public void updateDay(Day day) {
        tripDao.updateDay(day);
    }

    public void deleteDay(Day day) {
        TripperDatabase.databaseWriteExecutor.execute(() ->
                tripDao.deleteDay(day));
    }
    public void deleteDays(List<Day> days) {
        TripperDatabase.databaseWriteExecutor.execute(() ->
                tripDao.deleteDays(days));
    }
}


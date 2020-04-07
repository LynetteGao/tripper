package com.tripper.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.tripper.db.entities.Day;
import com.tripper.db.entities.Trip;
import com.tripper.db.relationships.TripWithDays;

import java.util.List;

@Dao
public interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTrip(Trip trip);

    @Delete
    public void deleteTrip(Trip trip);

    @Update
    public void updateTrip(Trip trip);

    @Insert
    public void insertDay(Day day);

    @Delete
    public void deleteDay(Day day);

    @Update
    public void updateDay(Day day);

    @Query("select * from trip order by start_date desc")
    LiveData<List<Trip>> getLiveTripsDesc();

    @Query("select * from trip order by start_date desc")
    List<Trip> getTripsDesc();

    @Query("select * from day where trip_id = :tripId")
    List<Day> getDaysByTripId(int tripId);

    @Transaction
    @Query("select * from trip")
    public List<TripWithDays> getTripsWithDays();

//    @Transaction
//    @Query("select * from daysegment")
//    public List<DaySegmentWithEvents> getDaySegmentsWithEvents();
//
//    @Transaction
//    @Query("select * from day")
//    public List<DayWithSegmentsAndEvents> getDaysWithSegmentsAndEvents();
//
//    @Transaction
//    @Query("select * from trip")
//    public List<TripWithDaysAndSegmentsAndEvents> getTripsWithDaysAndSegmentsAndEvents();

}

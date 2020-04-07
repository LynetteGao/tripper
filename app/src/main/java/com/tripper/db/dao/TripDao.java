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
import com.tripper.db.entities.DaySegment;
import com.tripper.db.entities.Event;
import com.tripper.db.entities.Trip;
import com.tripper.db.relationships.DaySegmentWithEvents;
import com.tripper.db.relationships.DayWithSegmentsAndEvents;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;

import java.util.List;

@Dao
public interface TripDao {

    // Simple insert, delete, update methods for tables
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTrip(Trip trip);

    @Delete
    public void deleteTrip(Trip trip);

    @Update
    public void updateTrip(Trip trip);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertDay(Day day);

    @Delete
    public void deleteDay(Day day);

    @Update
    public void updateDay(Day day);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertDaySegment(DaySegment daySegment);

    @Delete
    public void deleteDaySegment(DaySegment daySegment);

    @Update
    public void updateDaySegment(DaySegment daySegment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertEvent(Event event);

    @Delete
    public void deleteEvent(Event event);

    @Update
    public void  updateEvent(Event event);

    // simple select queries
    @Query("select * from trip")
    public List<Trip> getTrips();

    @Query("select * from day")
    public List<Day> getDays();

    @Query("select * from day_segment")
    public List<DaySegment> getDaySegments();

    @Query("select * from event")
    public List<Event> getEvents();

    // more complex data access methods
    @Query("select * from trip order by start_date desc")
    public LiveData<List<Trip>> getLiveTripsDesc();

    @Query("select * from trip order by start_date desc")
    public List<Trip> getTripsDesc();

    @Query("select * from day where trip_id = :tripId")
    public List<Day> getDaysByTripId(int tripId);

    @Query("select * from event where segment_id = :segmentId")
    public List<Event> getEventsBySegmentId(int segmentId);

    @Query("select * from day_segment where day_id = :dayId")
    public List<DaySegment> getDaySegmentsByDayId(int dayId);

    // relationship methods
    @Transaction
    @Query("select * from trip")
    public List<TripWithDaysAndDaySegments> getTripsWithDaysAndDaySegments();

    @Transaction
    @Query("select * from day_segment")
    public List<DaySegmentWithEvents> getDaySegmentsWithEvents();
//
    @Transaction
    @Query("select * from day")
    public List<DayWithSegmentsAndEvents> getDaysWithSegmentsAndEvents();
//
//    @Transaction
//    @Query("select * from trip")
//    public List<TripWithDaysAndSegmentsAndEvents> getTripsWithDaysAndSegmentsAndEvents();

}

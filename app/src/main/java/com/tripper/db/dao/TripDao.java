package com.tripper.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.tripper.db.entities.Trip;

import java.util.List;

@Dao
public interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTrip(Trip trip);

    @Delete
    public void deleteTrip(Trip trip);

    @Update
    public void updateTrip(Trip trip);

    @Query("select * from trip order by start_date desc")
    LiveData<List<Trip>> getTripsDesc();

}

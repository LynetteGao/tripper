package com.tripper.db.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.tripper.db.entities.Day;
import com.tripper.db.entities.Trip;

import java.util.List;

public class TripWithDays {
    @Embedded public Trip trip;
    @Relation(
            parentColumn = "id",
            entityColumn = "trip_id"
    )
    public List<Day> days;
}

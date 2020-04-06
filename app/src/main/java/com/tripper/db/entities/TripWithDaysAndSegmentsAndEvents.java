package com.tripper.db.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TripWithDaysAndSegmentsAndEvents {
    @Embedded public Trip trip;
    @Relation(
            entity = Trip.class,
            parentColumn = "id",
            entityColumn = "trip_id"
    )
    public List<DayWithSegmentsAndEvents> days;
}

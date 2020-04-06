package com.tripper.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Event {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="trip_id")
    public int tripId;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="location_lat")
    public String locationLat;

    @ColumnInfo(name="location_lon")
    public String locationLon;

    @ColumnInfo(name="segment_id")
    public int segmentId;
}

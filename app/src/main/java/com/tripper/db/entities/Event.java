package com.tripper.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "event")
public class Event {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name="trip_id")
    public long tripId;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="location_lat")
    public String locationLat;

    @ColumnInfo(name="location_lon")
    public String locationLon;

    @ColumnInfo(name="segment_id")
    public long segmentId;
}

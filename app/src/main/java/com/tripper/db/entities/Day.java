package com.tripper.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tripper.db.converters.CalendarTypeConverter;

import java.util.Calendar;

@Entity
public class Day {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="date")
    @TypeConverters({CalendarTypeConverter.class})
    public Calendar date;

    @ColumnInfo(name="trip_id")
    public int tripId;

    @ColumnInfo(name="location_name")
    public String locationName;

    @ColumnInfo(name="location_lat")
    public String locationLat;

    @ColumnInfo(name="location_lon")
    public String locationLon;
}

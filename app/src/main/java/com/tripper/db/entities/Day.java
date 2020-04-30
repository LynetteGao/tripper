package com.tripper.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tripper.db.converters.CalendarTypeConverter;

import java.util.Calendar;

@Entity(tableName = "day")
public class Day {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name="date")
    @TypeConverters({CalendarTypeConverter.class})
    public Calendar date;

    @ColumnInfo(name="trip_id")
    public long tripId;

    @ColumnInfo(name="location_name")
    public String locationName;

    @ColumnInfo(name="location_lat")
    public String locationLat;

    @ColumnInfo(name="location_lon")
    public String locationLon;
}

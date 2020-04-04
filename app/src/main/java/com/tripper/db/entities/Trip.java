package com.tripper.db.entities;


import android.location.Location;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tripper.db.converters.CalendarTypeConverter;
import com.tripper.db.converters.LocationTypeConverter;

import java.util.Calendar;

@Entity(tableName = "trip")
public class Trip {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="start_date")
    @TypeConverters({CalendarTypeConverter.class})
    public Calendar startDate;

    @ColumnInfo(name="end_date")
    @TypeConverters({CalendarTypeConverter.class})
    public Calendar endDate;

    @ColumnInfo(name="destination")
    public String destination;

    @ColumnInfo(name="location")
    @TypeConverters({LocationTypeConverter.class})
    public Location location;

}

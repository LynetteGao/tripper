package com.tripper.db.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tripper.db.converters.CalendarTypeConverter;

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

}

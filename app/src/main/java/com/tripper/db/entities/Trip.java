package com.tripper.db.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tripper.db.converters.DateTypeConverter;

import java.util.Date;

@Entity(tableName = "trip")
public class Trip {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="start_date")
    @TypeConverters({DateTypeConverter.class})
    public Date startDate;

    @ColumnInfo(name="end_date")
    @TypeConverters({DateTypeConverter.class})
    public Date endDate;

}

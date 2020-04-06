package com.tripper.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "daysegment")
public class DaySegment {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "segment")
    public int segment; // 0, 1 , or 2 for morning, afternoon, evening respectively

    @ColumnInfo(name= "day_id")
    public int dayId;
}

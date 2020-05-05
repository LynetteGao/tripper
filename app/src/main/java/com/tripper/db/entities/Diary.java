package com.tripper.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Diary {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "segment_id")
    public long segmentId;

}

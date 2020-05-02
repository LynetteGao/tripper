package com.tripper.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DiaryEntry {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "diary_id")
    public long diaryId;

    @ColumnInfo(name = "diary_text")
    public String diaryText;

}

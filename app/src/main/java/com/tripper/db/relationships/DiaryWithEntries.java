package com.tripper.db.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.tripper.db.entities.Diary;
import com.tripper.db.entities.DiaryEntry;

import java.util.List;

public class DiaryWithEntries {
    @Embedded public Diary diary;

    @Relation(
            parentColumn = "id",
            entityColumn = "diary_id"   )
    public List<DiaryEntry> diaryEntries;
}

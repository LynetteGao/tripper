package com.tripper.db.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.tripper.db.entities.DaySegment;
import com.tripper.db.entities.Diary;
import com.tripper.db.entities.Event;

import java.util.List;

public class DaySegmentWithEvents {
    @Embedded public DaySegment daySegment;
    @Relation(
            parentColumn = "id",
            entityColumn = "segment_id"
    )
    public List<Event> events;

    @Relation(
            entity = Diary.class,
            parentColumn = "id",
            entityColumn = "segment_id"
    )
    public DiaryWithEntries diaryWithEntries;
}

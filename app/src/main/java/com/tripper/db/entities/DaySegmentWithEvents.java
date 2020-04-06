package com.tripper.db.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DaySegmentWithEvents {
    @Embedded public DaySegment daySegment;
    @Relation(
            parentColumn = "id",
            entityColumn = "segment_id"
    )
    public List<Event> events;
}

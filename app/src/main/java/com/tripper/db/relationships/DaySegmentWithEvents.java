package com.tripper.db.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.tripper.db.entities.DaySegment;
import com.tripper.db.entities.Event;

import java.util.List;

public class DaySegmentWithEvents {
    @Embedded public DaySegment daySegment;
    @Relation(
            parentColumn = "id",
            entityColumn = "segment_id"
    )
    public List<Event> events;
}

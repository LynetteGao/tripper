package com.tripper.db.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DayWithSegmentsAndEvents {
    @Embedded public Day day;
    @Relation(
            entity = DaySegment.class,
            parentColumn = "id",
            entityColumn = "day_id"
    )
    public List<DaySegmentWithEvents> daySegments;
}

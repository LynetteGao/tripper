package com.tripper.db.relationships;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import com.tripper.db.entities.Day;
import com.tripper.db.entities.DaySegment;

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

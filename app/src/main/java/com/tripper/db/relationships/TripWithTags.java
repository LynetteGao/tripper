package com.tripper.db.relationships;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.tripper.db.entities.Tag;
import com.tripper.db.entities.Trip;
import com.tripper.db.entities.TripTagCrossRef;

import java.util.List;

public class TripWithTags {
    @Embedded public Trip trip;
    @Relation(
            parentColumn = "tripId",
            entityColumn = "tagId",
            associateBy = @Junction(TripTagCrossRef.class)
    )
    public List<Tag> tags;
}

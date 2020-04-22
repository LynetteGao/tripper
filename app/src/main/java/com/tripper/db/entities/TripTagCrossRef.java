package com.tripper.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "trip_tag_join",
        primaryKeys = {"tripId", "tagId"})
public class TripTagCrossRef {
    public final int tagId;
    public final int tripId;

    public TripTagCrossRef(final int tagId, final int tripId) {
        this.tagId = tagId;
        this.tripId = tripId;
    }
}

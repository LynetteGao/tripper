package com.tripper.db.entities;

import androidx.room.Entity;

@Entity(tableName = "trip_tag_join",
        primaryKeys = {"tripId", "tagId"})
public class TripTagCrossRef {
    public final long tagId;
    public final long tripId;

    public TripTagCrossRef(final long tagId, final long tripId) {
        this.tagId = tagId;
        this.tripId = tripId;
    }
}

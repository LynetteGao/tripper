package com.tripper.db.entities;

import androidx.room.Entity;

@Entity(primaryKeys = {"tripId", "tagId"})
public class TripTagCrossRef {
    public int tripId;
    public int tagId;
}

package com.tripper.db.entities;

import androidx.room.Entity;

@Entity(tableName = "event_tag_join",
        primaryKeys = {"eventId", "tagId"})
public class EventTagCrossRef {
    public final long eventId;
    public final long tagId;

    public EventTagCrossRef(final long eventId, final long tagId) {
        this.eventId = eventId;
        this.tagId = tagId;
    }
}

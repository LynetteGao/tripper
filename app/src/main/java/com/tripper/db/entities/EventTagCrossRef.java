package com.tripper.db.entities;

import androidx.room.Entity;

@Entity(tableName = "event_tag_join",
        primaryKeys = {"eventId", "tagId"})
public class EventTagCrossRef {
    public final int eventId;
    public final int tagId;

    public EventTagCrossRef(final int eventId, final int tagId) {
        this.eventId = eventId;
        this.tagId = tagId;
    }
}

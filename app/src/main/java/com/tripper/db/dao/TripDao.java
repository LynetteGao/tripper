package com.tripper.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.tripper.db.entities.Day;
import com.tripper.db.entities.DaySegment;
import com.tripper.db.entities.Diary;
import com.tripper.db.entities.DiaryEntry;
import com.tripper.db.entities.Event;
import com.tripper.db.entities.Tag;
import com.tripper.db.entities.Trip;
import com.tripper.db.entities.TripTagCrossRef;
import com.tripper.db.relationships.DaySegmentWithEvents;
import com.tripper.db.relationships.DayWithSegmentsAndEvents;
import com.tripper.db.relationships.DiaryWithEntries;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;
import com.tripper.db.relationships.TripWithTags;

import java.util.List;

@Dao
public abstract class TripDao {

    // Simple insert, delete, update methods for tables
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertTrip(Trip trip);

    @Delete
    public abstract void deleteTrip(Trip trip);

    @Update
    public abstract void updateTrip(Trip trip);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertDay(Day day);

    @Delete
    public abstract void deleteDay(Day day);

    @Update
    public abstract void updateDay(Day day);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertDaySegment(DaySegment daySegment);

    @Delete
    public abstract void deleteDaySegment(DaySegment daySegment);

    @Update
    public abstract void updateDaySegment(DaySegment daySegment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertEvent(Event event);

    @Delete
    public abstract void deleteEvent(Event event);

    @Update
    public  abstract void  updateEvent(Event event);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertTag(Tag tag);

    @Delete
    public abstract void deleteTag(Tag tag);

    @Update
    public abstract void updateTag(Tag tag);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertDiary(Diary diary);

    @Delete
    public abstract void deleteDiary(Diary diary);

    @Update
    public abstract void updateDiary(Diary diary);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertDiaryEntry(DiaryEntry diaryEntry);

    @Delete
    public abstract void deleteDiaryEntry(DiaryEntry diaryEntry);

    @Update
    public abstract void updateDiaryEntry(DiaryEntry diaryEntry);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertTags(List<Tag> tags);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertTripTag(TripTagCrossRef tripTagCrossRef);

    @Delete
    public abstract void deleteTripTag(TripTagCrossRef tripTagCrossRef);

    @Update
    public abstract void updateTripTag(TripTagCrossRef tripTagCrossRef);

    @Delete
    public abstract void deleteEvents(List<Event> events);

    @Delete
    public abstract void deleteDaySegments(List<DaySegment> daySegments);

    @Delete
    public abstract void deleteDays(List<Day> days);

    // simple select queries
    @Query("select * from trip")
    public abstract List<Trip> getTrips();

    @Query("select * from day")
    public abstract List<Day> getDays();

    @Query("select * from day_segment")
    public abstract List<DaySegment> getDaySegments();

    @Query("select * from event")
    public abstract List<Event> getEvents();

    @Query("select * from tag")
    public abstract List<Tag> getTags();

    @Query("select * from diary")
    public abstract List<Diary> getDiaries();

    @Query("select * from diaryentry")
    public abstract List<DiaryEntry> getDiaryEntries();

    // more complex data access methods
    @Query("select * from trip order by start_date desc")
    public abstract LiveData<List<Trip>> getLiveTripsDesc();

    @Query("select * from trip order by start_date desc")
    public abstract List<Trip> getTripsDesc();

    @Query("select * from day where trip_id = :tripId")
    public abstract List<Day> getDaysByTripId(int tripId);

    @Query("select * from event where segment_id = :segmentId")
    public abstract List<Event> getEventsBySegmentId(int segmentId);

    @Query("select * from day_segment where day_id = :dayId")
    public abstract List<DaySegment> getDaySegmentsByDayId(int dayId);

    @Query("select * from tag inner join trip_tag_join on tag.id=trip_tag_join.tagId where trip_tag_join.tripId = :tripId")
    public abstract List<Tag> getTagsForTrip(int tripId);

    @Query("select * from tag inner join event_tag_join on tag.id=event_tag_join.tagId where event_tag_join.eventId = :eventId")
    public abstract List<Tag> getTagsForEvent(int eventId);

    @Query("select * from trip order by id desc limit 1")
    public abstract LiveData<Trip> getMostRecentTrip();

    @Query("select * from tag")
    public abstract LiveData<List<Tag>> getLiveTags();



    // relationship methods
    @Transaction
    @Query("select * from trip")
    public abstract List<TripWithDaysAndDaySegments> getTripsWithDaysAndDaySegments();

    @Transaction
    @Query("select * from day_segment")
    public abstract List<DaySegmentWithEvents> getDaySegmentsWithEvents();
//
    @Transaction
    @Query("select * from day")
    public abstract List<DayWithSegmentsAndEvents> getDaysWithSegmentsAndEvents();

    @Transaction
    @Query("select * from trip where id = :tripId")
    public abstract TripWithDaysAndDaySegments getTripWithDaysAndDaySegmentsById(Long tripId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertTripTags(List<TripTagCrossRef> tripTagCrossRefs);

    @Transaction
    @Query("select * from trip")
    public abstract List<TripWithDaysAndDaySegments> getTripsWithDaysAndSegmentsAndEvents();

    @Transaction
    @Query("select * from trip where id = :tripId")
    public abstract TripWithTags getTripWithTags(long tripId);

    @Transaction
    @Query("select * from diary where segment_id = :segmentId")
    public abstract Diary getDiaryById(long segmentId);

    @Transaction
    @Query("select * from diary")
    public abstract List<DiaryWithEntries> getDiariesWithEntries();

    @Query("select * from diaryentry where diary_id = :diaryId")
    public abstract List<DiaryEntry> getDiaryEntriesById(long diaryId);

}

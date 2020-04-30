package com.tripper;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.tripper.db.TripperDatabase;
import com.tripper.db.dao.TripDao;
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private TripDao tripDao;
    private TripperDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TripperDatabase.class).build();
        tripDao = db.tripDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    // simple insert and retrieve tests
    @Test
    public void writeTrip() {
        Trip trip = createTestTrip();
        tripDao.insertTrip(trip);
        List<Trip> trips = tripDao.getTripsDesc();

        assertEquals(trips.get(0).destination, "test destination");

    }

    @Test
    public void writeDay() {
        Day day = createTestDay();
        day.tripId = 0;
        tripDao.insertDay(day);

        List<Day> days = tripDao.getDaysByTripId(0);
        assertNotNull(days);
        assertEquals(days.get(0).locationName, "test location");
    }

    @Test
    public void writeDaySegment() {
        DaySegment daySegment = createTestDaySegment();
        daySegment.dayId = 0;
        tripDao.insertDaySegment(daySegment);

        List<DaySegment> daySegments = tripDao.getDaySegmentsByDayId(0);
        assertEquals(daySegments.get(0).segment, 0);
    }

    @Test
    public void writeEvent() {
        Event event = createTestEvent();
        event.segmentId = 0;
        tripDao.insertEvent(event);

        List<Event> events = tripDao.getEventsBySegmentId(0);
        assertEquals(events.get(0).name, "test event");

    }

    @Test
    public void writeTag() {
        Tag tag = createTestTag();
        tripDao.insertTag(tag);

        List<Tag> tags = tripDao.getTags();
        assertEquals(tags.get(0).name, tag.name);
    }

    @Test
    public void writeDiary() {
        Diary diary = createTestDiary();
        tripDao.insertDiary(diary);

        List<Diary> diaries = tripDao.getDiaries();
        assertEquals(diaries.get(0).segmentId, diary.segmentId);
    }

    @Test
    public void writeDiaryEntry() {
        DiaryEntry diaryEntry = createTestDiaryEntry();
        tripDao.insertDiaryEntry(diaryEntry);

        List<DiaryEntry> diaryEntries = tripDao.getDiaryEntries();
        assertEquals(diaryEntries.get(0).diaryText, diaryEntry.diaryText);

    }



    // test relationship classes

    @Test
    public void getDaySegmentWithEvents() {
        DaySegment daySegment = createTestDaySegment();
        tripDao.insertDaySegment(daySegment);
        daySegment = tripDao.getDaySegments().get(0);

        Event event = createTestEvent();
        event.segmentId = daySegment.id;
        tripDao.insertEvent(event);

        List<DaySegmentWithEvents> daySegmentWithEvents = tripDao.getDaySegmentsWithEvents();
        assertEquals(daySegmentWithEvents.get(0).daySegment.id,
                daySegmentWithEvents.get(0).events.get(0).segmentId);
    }

    @Test
    public void getDayWithSegmentsAndEvents() {
        Day day = createTestDay();
        tripDao.insertDay(day);
        day = tripDao.getDays().get(0);

        DaySegment daySegment = createTestDaySegment();
        daySegment.dayId = day.id;
        tripDao.insertDaySegment(daySegment);
        daySegment = tripDao.getDaySegments().get(0);

        Event event = createTestEvent();
        event.segmentId = daySegment.id;
        tripDao.insertEvent(event);

        List<DayWithSegmentsAndEvents> dayWithSegmentsAndEvents = tripDao.getDaysWithSegmentsAndEvents();
        assertEquals(dayWithSegmentsAndEvents.get(0).day.id,
                dayWithSegmentsAndEvents.get(0).daySegments.get(0).daySegment.dayId);

    }

    @Test
    public void getTripWithDaysAndDaySegments() {
        Trip trip = createTestTrip();
        tripDao.insertTrip(trip);
        trip = tripDao.getTripsDesc().get(0);

        Day day = createTestDay();
        day.tripId = trip.id;
        tripDao.insertDay(day);
        day = tripDao.getDays().get(0);

        DaySegment daySegment = createTestDaySegment();
        daySegment.dayId = day.id;
        tripDao.insertDaySegment(daySegment);
        daySegment = tripDao.getDaySegments().get(0);

        Event event = createTestEvent();
        event.segmentId = daySegment.id;
        tripDao.insertEvent(event);

        List<TripWithDaysAndDaySegments> tripWithDays = tripDao.getTripsWithDaysAndDaySegments();
        assertNotNull(tripWithDays);
        assertEquals(tripWithDays.get(0).trip.id,
                tripWithDays.get(0).days.get(0).day.tripId);
        //assertEquals(tripWithDays.get(0).trip.id, tripWithDays.get(0).days.get(0).tripId);

    }

    @Test
    public void getTripWithDaysAndSegmentsById() {
        Trip trip = createTestTrip();
        tripDao.insertTrip(trip);
        trip = tripDao.getTripsDesc().get(0);

        Day day = createTestDay();
        day.tripId = trip.id;
        tripDao.insertDay(day);
        day = tripDao.getDays().get(0);

        DaySegment daySegment = createTestDaySegment();
        daySegment.dayId = day.id;
        tripDao.insertDaySegment(daySegment);
        daySegment = tripDao.getDaySegments().get(0);

        Event event = createTestEvent();
        event.segmentId = daySegment.id;
        tripDao.insertEvent(event);

        TripWithDaysAndDaySegments tripWithDaysAndDaySegments = tripDao.getTripWithDaysAndDaySegmentsById(trip.id);
        assertEquals(tripWithDaysAndDaySegments.trip.id, trip.id);
    }

    @Test
    public void getTripWithTagsById() {
//        Trip trip = createTestTrip();
//        Tag tag = createTestTag();
//        tripDao.insertTrip(trip);
//        tripDao.insertTag(tag);
//
//        trip = tripDao.getTrips().get(0);
//        tag = tripDao.getTags().get(0);
//
//        TripTagCrossRef tripTagCrossRef = new TripTagCrossRef(tag.id, trip.id);
//        tripDao.insertTripTag(tripTagCrossRef);
//
//        List<Tag> tags = tripDao.getTagsForTrip(trip.id);
//        assertEquals(tags.get(0).name, tag.name);


    }

    @Test
    public void getMostRecentTrip() {
//        Trip trip1 = createTestTrip();
//        Trip trip2 = createTestTrip();
//        trip2.name = "test trip2";
//        tripDao.insertTrip(trip1);
//        tripDao.insertTrip(trip2);
//
//        Trip recTrip = tripDao.getMostRecentTrip();
//        assertEquals(trip2.name, recTrip.name);
    }

    @Test
    public void getDiaryWithEntries() {

        Diary diary = createTestDiary();
        long diaryId = tripDao.insertDiary(diary);

        DiaryEntry diaryEntry = createTestDiaryEntry();
        diaryEntry.diaryId = diaryId;
        tripDao.insertDiaryEntry(diaryEntry);

        List<DiaryWithEntries> diaries = tripDao.getDiaryWithEntries();
        assertEquals(diaries.get(0).diary.id, diaryId);
        assertEquals(diaries.get(0).diaryEntries.get(0).diaryText, diaryEntry.diaryText);
    }

    @Test
    public void getDaySegmentsWithDiaries() {
        DaySegment daySegment = createTestDaySegment();
        long segmentId = tripDao.insertDaySegment(daySegment);

        Diary diary = createTestDiary();
        diary.segmentId = segmentId;
        long diaryId = tripDao.insertDiary(diary);

        DiaryEntry diaryEntry = createTestDiaryEntry();
        diaryEntry.diaryId = diaryId;
        long entryId = tripDao.insertDiaryEntry(diaryEntry);

        List<DaySegmentWithEvents> daySegmentWithEvents = tripDao.getDaySegmentsWithEvents();
        assertEquals(daySegmentWithEvents.get(0).diaryWithEntries.diary.id, diaryId);
    }

    // helper methods
    private Trip createTestTrip() {
        Trip trip = new Trip();
        trip.name = "test name";
        trip.destination = "test destination";
        trip.startDate = Calendar.getInstance();
        trip.startDate.add(Calendar.DATE, 1);
        trip.endDate = Calendar.getInstance();
        trip.endDate.add(Calendar.DATE, 7);
        trip.locationLat = "0";
        trip.locationLon = "0";
        return trip;
    }

    private Day createTestDay() {
        Day day = new Day();
        day.date = Calendar.getInstance();
        day.locationLat = "0";
        day.locationLon = "0";
        day.locationName = "test location";
        return day;
    }

    private DaySegment createTestDaySegment() {
        DaySegment daySegment = new DaySegment();
        daySegment.segment = 0;
        return daySegment;
    }

    private Event createTestEvent() {
        Event event = new Event();
        event.locationLat = "0";
        event.locationLon = "0";
        event.name = "test event";
        return event;
    }

    private Tag createTestTag() {
        Tag tag = new Tag("testTag", "testIcon");
        return tag;
    }

    private Diary createTestDiary() {
        Diary diary = new Diary();
        diary.segmentId = 0;
        return diary;
    }

    private DiaryEntry createTestDiaryEntry() {
        DiaryEntry diaryEntry = new DiaryEntry();
        diaryEntry.diaryId = 1;
        diaryEntry.diaryText = "this is a test entry";
        return diaryEntry;
    }

}

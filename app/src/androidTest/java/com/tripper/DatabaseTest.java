package com.tripper;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.tripper.db.TripperDatabase;
import com.tripper.db.dao.TripDao;
import com.tripper.db.entities.Day;
import com.tripper.db.entities.DaySegment;
import com.tripper.db.entities.Event;
import com.tripper.db.entities.Trip;
import com.tripper.db.relationships.DaySegmentWithEvents;
import com.tripper.db.relationships.DayWithSegmentsAndEvents;
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
        //assertEquals(tripWithDays.get(0).trip.id, tripWithDays.get(0).days.get(0).tripId);

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

}

package com.tripper;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.tripper.db.TripperDatabase;
import com.tripper.db.dao.TripDao;
import com.tripper.db.entities.Day;
import com.tripper.db.entities.Trip;
import com.tripper.db.relationships.TripWithDays;

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
public class SimpleEntityTest {
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
    public void getTripWithDays() {
        Trip trip = createTestTrip();
        tripDao.insertTrip(trip);
        trip = tripDao.getTripsDesc().get(0);

        Day day = createTestDay();
        day.tripId = trip.id;
        tripDao.insertDay(day);

        List<TripWithDays> tripWithDays = tripDao.getTripsWithDays();
        assertNotNull(tripWithDays);

    }

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


}

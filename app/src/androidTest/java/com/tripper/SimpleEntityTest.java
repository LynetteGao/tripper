package com.tripper;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.tripper.db.TripperDatabase;
import com.tripper.db.dao.TripDao;
import com.tripper.db.entities.Trip;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
        Trip trip = new Trip();
        trip.name = "test name";
        trip.destination = "test destination";
        trip.startDate = Calendar.getInstance();
        trip.startDate.add(Calendar.DATE, 1);
        trip.endDate = Calendar.getInstance();
        trip.endDate.add(Calendar.DATE, 7);
        trip.locationLat = "0";
        trip.locationLon = "0";
        tripDao.insertTrip(trip);
        List<Trip> trips = tripDao.getTripsDesc();

        assertEquals(trips.get(0).destination, "test destination");

    }
}

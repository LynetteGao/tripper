package com.tripper.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tripper.db.converters.CalendarTypeConverter;
import com.tripper.db.dao.TripDao;
import com.tripper.db.entities.Day;
import com.tripper.db.entities.DaySegment;
import com.tripper.db.entities.Event;
import com.tripper.db.entities.Trip;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Trip.class, Day.class, DaySegment.class, Event.class,}, version = 3, exportSchema = false)
@TypeConverters({CalendarTypeConverter.class})
public abstract class TripperDatabase extends RoomDatabase {
    public abstract TripDao tripDao();

    private static volatile TripperDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TripperDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TripperDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TripperDatabase.class, "tripper_database")
                            .build();
                }
            }
        }

        return INSTANCE;
    }

}

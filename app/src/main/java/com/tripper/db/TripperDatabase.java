package com.tripper.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tripper.db.converters.CalendarTypeConverter;
import com.tripper.db.dao.TripDao;
import com.tripper.db.entities.Day;
import com.tripper.db.entities.DaySegment;
import com.tripper.db.entities.Event;
import com.tripper.db.entities.EventTagCrossRef;
import com.tripper.db.entities.Tag;
import com.tripper.db.entities.Trip;
import com.tripper.db.entities.TripTagCrossRef;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Trip.class, Day.class, DaySegment.class, Event.class, Tag.class,
        TripTagCrossRef.class, EventTagCrossRef.class}, version = 5, exportSchema = false)
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
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    AsyncTask.execute(() -> {
                                        getDatabase(context).tripDao().insertTags(createDefaultTags());
                                    });
                                }
                            })
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    static private List<Tag> createDefaultTags() {
        List<Tag> tagList = new ArrayList<Tag>();
        tagList.add(new Tag("Sightseeing", "sightseeing"));
        tagList.add(new Tag("Art", "art"));
        tagList.add(new Tag("Sport", "sport"));
        tagList.add(new Tag("History", "history"));
        tagList.add(new Tag("Leisure", "leisure"));
        tagList.add(new Tag("Eateries", "eateries"));
        return tagList;
    }
}

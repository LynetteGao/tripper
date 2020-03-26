package com.tripper.db.converters;

import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

public class CalendarTypeConverter {

    @TypeConverter
    public static Calendar toDate(Long value) {
        if (value == null)
            return null;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(value);
        return cal;
    }

    @TypeConverter
    public static Long toLong (Calendar value) {
        if (value == null)
            return null;

        return value.getTimeInMillis();
    }
}

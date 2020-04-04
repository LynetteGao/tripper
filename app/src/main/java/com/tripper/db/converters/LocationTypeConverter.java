package com.tripper.db.converters;

import android.location.Location;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class LocationTypeConverter {
    @TypeConverter
    public static Location toLocation(String locationString) {
        Gson gson = new Gson();
        return gson.fromJson(locationString, Location.class);
    }

    @TypeConverter
    public static String toLocationString(Location location) {
        Gson gson = new Gson();
        return gson.toJson(location);
    }
}

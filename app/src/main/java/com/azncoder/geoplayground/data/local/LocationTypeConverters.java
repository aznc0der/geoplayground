package com.azncoder.geoplayground.data.local;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by aznc0der on 2/1/2018.
 */

public class LocationTypeConverters {
    @TypeConverter
    public static Location stringToLocation(String json) {
        return new Gson().fromJson(json, Location.class);
    }

    @TypeConverter
    public static String locationToString(Location location) {
        return new Gson().toJson(location, Location.class);
    }
}

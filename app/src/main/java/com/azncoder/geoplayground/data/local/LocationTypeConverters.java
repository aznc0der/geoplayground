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
        Gson gson = new Gson();
        Type type = new TypeToken<Location>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String locationToString(Location location) {
        Gson gson = new Gson();
        Type type = new TypeToken<Location>() {
        }.getType();
        return gson.toJson(location, type);
    }
}

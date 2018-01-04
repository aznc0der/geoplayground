package com.azncoder.geoplayground.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by aznc0der on 3/1/2018.
 */
@Database(entities = {Delivery.class}, version = DeliveryDatabase.VERSION, exportSchema = false)
@TypeConverters({LocationTypeConverters.class})
public abstract class DeliveryDatabase extends RoomDatabase {
    static final int VERSION = 1;

    public abstract DeliveryDao getDeliveryDao();
}

package com.azncoder.geoplayground.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by aznc0der on 3/1/2018.
 */

@Dao
public interface DeliveryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Delivery> deliveries);

    @Query("SELECT * FROM Delivery")
    Flowable<List<Delivery>> loadAll();

    @Query("DELETE FROM Delivery")
    void deleteAllDeliveries();
}

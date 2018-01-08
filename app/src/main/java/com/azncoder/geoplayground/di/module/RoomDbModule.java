package com.azncoder.geoplayground.di.module;

import android.arch.persistence.room.Room;

import com.azncoder.geoplayground.MainApplication;
import com.azncoder.geoplayground.data.local.DeliveryDao;
import com.azncoder.geoplayground.data.local.DeliveryDatabase;
import com.azncoder.geoplayground.data.local.LocalService;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aznc0der on 2/1/2018.
 */
@Module
public class RoomDbModule {
    @Provides
    @Singleton
    DeliveryDatabase providesDeliveryDatabase(MainApplication application) {
        return Room.databaseBuilder(application, DeliveryDatabase.class, "Delivery").fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    DeliveryDao providesDeliveryDao(DeliveryDatabase deliveryDatabase) {
        return deliveryDatabase.getDeliveryDao();
    }

    @Provides
    @Singleton
    LocalService providesLocalService(DeliveryDao deliveryDao, Executor executor) {
        return new LocalService(deliveryDao, executor);
    }
}

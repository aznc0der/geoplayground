package com.azncoder.geoplayground;

import android.app.Application;

import com.azncoder.geoplayground.di.Components;
import com.azncoder.geoplayground.di.DaggerComponents;
import com.azncoder.geoplayground.di.module.AppModule;
import com.azncoder.geoplayground.di.module.NetworkModule;
import com.azncoder.geoplayground.di.module.RoomDbModule;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;

/**
 * Created by aznc0der on 1/1/2018.
 */

public class MainApplication extends Application {
    public Components mComponents;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        File cacheFileDir = new File(getCacheDir(), "cache");
        mComponents = DaggerComponents.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(AppConfig.REST_API_BASE_URL, cacheFileDir))
                .roomDbModule(new RoomDbModule())
                .build();
    }

    public Components getComponents() {
        return mComponents;
    }
}

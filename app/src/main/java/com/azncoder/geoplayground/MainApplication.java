package com.azncoder.geoplayground;

import android.app.Application;

import com.azncoder.geoplayground.di.AppComponents;
import com.azncoder.geoplayground.di.DaggerAppComponents;
import com.azncoder.geoplayground.di.module.AppModule;
import com.azncoder.geoplayground.di.module.NetworkModule;
import com.azncoder.geoplayground.di.module.RoomDbModule;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;

/**
 * Created by aznc0der on 1/1/2018.
 */

public class MainApplication extends Application {
    public AppComponents mComponents;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        initDaggerComponents("cache");
    }

    public void initDaggerComponents(String cacheDir) {
        mComponents = DaggerAppComponents.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(AppConfig.REST_API_BASE_URL, new File(getCacheDir(), cacheDir)))
                .roomDbModule(new RoomDbModule())
                .build();
    }

    public AppComponents getComponents() {
        return mComponents;
    }
}

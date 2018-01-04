package com.azncoder.geoplayground.di.module;

import com.azncoder.geoplayground.MainApplication;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aznc0der on 1/1/2018.
 */
@Module
public class AppModule {
    MainApplication mApplication;

    public AppModule(MainApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    MainApplication providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Executor providesExecutor() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }
}

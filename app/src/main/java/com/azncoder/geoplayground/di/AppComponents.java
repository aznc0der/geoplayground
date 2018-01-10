package com.azncoder.geoplayground.di;

import com.azncoder.geoplayground.di.module.AppModule;
import com.azncoder.geoplayground.di.module.DetailFragmentModule;
import com.azncoder.geoplayground.di.module.HomeFragmentModule;
import com.azncoder.geoplayground.di.module.NetworkModule;
import com.azncoder.geoplayground.di.module.RoomDbModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by aznc0der on 1/1/2018.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, RoomDbModule.class})
public interface AppComponents {
    HomeComponent with(HomeFragmentModule homeFragmentModule);

    DetailComponent with(DetailFragmentModule detailFragmentModule);
}

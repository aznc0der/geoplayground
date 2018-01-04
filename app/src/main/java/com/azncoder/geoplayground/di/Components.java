package com.azncoder.geoplayground.di;

import com.azncoder.geoplayground.detail.DetailActivity;
import com.azncoder.geoplayground.di.module.AppModule;
import com.azncoder.geoplayground.di.module.NetworkModule;
import com.azncoder.geoplayground.di.module.RoomDbModule;
import com.azncoder.geoplayground.home.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by aznc0der on 1/1/2018.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, RoomDbModule.class})
public interface Components {
    void inject(HomeActivity activity);

    void inject(DetailActivity activity);
}

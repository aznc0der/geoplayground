package com.azncoder.geoplayground.di.module;

import com.azncoder.geoplayground.data.local.LocalService;
import com.azncoder.geoplayground.data.remote.NetworkService;
import com.azncoder.geoplayground.di.scope.FragmentScope;
import com.azncoder.geoplayground.home.HomePresenter;
import com.azncoder.geoplayground.home.HomeView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aznc0der on 9/1/2018.
 */
@Module
public class HomeFragmentModule {

    private final HomeView mView;

    public HomeFragmentModule(HomeView view) {
        mView = view;
    }

    @Provides
    @FragmentScope
    HomePresenter provideHomePresenter(LocalService localService, NetworkService networkService) {
        return new HomePresenter(localService, networkService, provideHomeView());
    }

    @Provides
    @FragmentScope
    HomeView provideHomeView() {
        return mView;
    }
}

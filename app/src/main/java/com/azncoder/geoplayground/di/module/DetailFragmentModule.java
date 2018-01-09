package com.azncoder.geoplayground.di.module;

import com.azncoder.geoplayground.detail.DetailPresenter;
import com.azncoder.geoplayground.detail.DetailView;
import com.azncoder.geoplayground.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aznc0der on 9/1/2018.
 */
@Module
public class DetailFragmentModule {

    private final DetailView mView;

    public DetailFragmentModule(DetailView view) {
        mView = view;
    }

    @Provides
    @FragmentScope
    DetailPresenter providePresenter() {
        return new DetailPresenter(provideDetailView());
    }

    @Provides
    @FragmentScope
    DetailView provideDetailView() {
        return mView;
    }
}

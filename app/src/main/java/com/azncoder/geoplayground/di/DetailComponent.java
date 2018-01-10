package com.azncoder.geoplayground.di;

import com.azncoder.geoplayground.detail.DetailFragment;
import com.azncoder.geoplayground.di.module.DetailFragmentModule;
import com.azncoder.geoplayground.di.scope.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by aznc0der on 9/1/2018.
 */
@FragmentScope
@Subcomponent(modules = {DetailFragmentModule.class})
public interface DetailComponent {
    void inject(DetailFragment detailFragment);
}

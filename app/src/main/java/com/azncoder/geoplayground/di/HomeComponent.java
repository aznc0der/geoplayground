package com.azncoder.geoplayground.di;

import com.azncoder.geoplayground.di.module.HomeFragmentModule;
import com.azncoder.geoplayground.di.scope.FragmentScope;
import com.azncoder.geoplayground.home.HomeFragment;

import dagger.Subcomponent;

/**
 * Created by aznc0der on 9/1/2018.
 */
@FragmentScope
@Subcomponent(modules = {HomeFragmentModule.class})
public interface HomeComponent {
    void inject(HomeFragment homeFragment);
}
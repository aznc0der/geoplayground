package com.azncoder.geoplayground.home;

import com.azncoder.geoplayground.R;
import com.azncoder.geoplayground.common.BaseFragment;

/**
 * Created by aznc0der on 2/1/2018.
 */

public class HomeFragment extends BaseFragment {

    @Override
    public int getLayoutViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public int getToolbarTitle() {
        return R.string.title_main_activity;
    }

    @Override
    public boolean enableHomeAsUp() {
        return false;
    }
}

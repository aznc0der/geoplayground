package com.azncoder.geoplayground.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.azncoder.geoplayground.R;
import com.azncoder.geoplayground.common.BaseActivity;

public class HomeActivity extends BaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected boolean isTabletMode() {
        return findViewById(R.id.right_fragment) != null;
    }
}

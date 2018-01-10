package com.azncoder.geoplayground.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by aznc0der on 1/1/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract int getContentViewId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (!isFinishing()) {
            onBackPressed();
        }
        return true;
    }
}

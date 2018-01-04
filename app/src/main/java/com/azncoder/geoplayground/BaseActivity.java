package com.azncoder.geoplayground;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Created by aznc0der on 1/1/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;

    public abstract int getContentViewId();

    public abstract int getToolbarTitle();

    public abstract void renderView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setupSupportActionBar(getSupportActionBar());
        renderView();
    }

    private void setupSupportActionBar(ActionBar supportActionBar) {
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
            ((TextView) mToolbar.findViewById(R.id.title)).setText(getToolbarTitle());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (!isFinishing()) {
            onBackPressed();
        }
        return true;
    }
}

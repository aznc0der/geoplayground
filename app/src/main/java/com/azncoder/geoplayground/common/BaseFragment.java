package com.azncoder.geoplayground.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.azncoder.geoplayground.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aznc0der on 9/1/2018.
 */

public abstract class BaseFragment extends Fragment {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.title)
    public TextView toolbarTitle;

    public abstract int getLayoutViewId();

    public abstract int getToolbarTitle();

    public abstract boolean enableHomeAsUp();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutViewId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setupSupportActionBar(((AppCompatActivity) getActivity()).getSupportActionBar());
    }

    private void setupSupportActionBar(ActionBar supportActionBar) {
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(enableHomeAsUp());
            supportActionBar.setDisplayShowHomeEnabled(enableHomeAsUp());
            toolbarTitle.setText(getToolbarTitle());
        }
    }
}

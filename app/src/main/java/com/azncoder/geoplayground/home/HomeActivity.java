package com.azncoder.geoplayground.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.azncoder.geoplayground.BaseActivity;
import com.azncoder.geoplayground.IntentIdentifier;
import com.azncoder.geoplayground.MainApplication;
import com.azncoder.geoplayground.R;
import com.azncoder.geoplayground.data.local.Delivery;
import com.azncoder.geoplayground.data.local.LocalService;
import com.azncoder.geoplayground.data.remote.NetworkService;
import com.azncoder.geoplayground.detail.DetailActivity;
import com.azncoder.geoplayground.detail.DetailFragment;
import com.azncoder.geoplayground.detail.EmptyFragment;
import com.azncoder.geoplayground.home.adapter.HomeAdapter;
import com.azncoder.geoplayground.widget.LoadingDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class HomeActivity extends BaseActivity implements HomeView {
    private final String TAG = HomeActivity.class.getSimpleName();
    @BindView(R.id.container)
    ViewGroup container;
    @BindView(R.id.rv_recycle)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Inject
    public NetworkService mNetworkService;
    @Inject
    public LocalService mLocalService;
    private HomePresenter mPresenter;
    private boolean isTabletMode;
    private HomeAdapter mAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    public int getToolbarTitle() {
        return R.string.title_main_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApplication) getApplication()).getComponents().inject(this);
        mPresenter = new HomePresenter(mLocalService, mNetworkService, this);
        if (findViewById(R.id.detailFragment) != null) {
            // The detail view will be present only in the large-screen layouts (res/values-w900dp).
            // If this view is present, then the activity should be in tablet mode.
            isTabletMode = true;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HomeAdapter(it -> navigateToDetailView(((Delivery) it)));
        recyclerView.setAdapter(mAdapter);
        swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.getDeliveriesFromRemote(false));
        mPresenter.getDeliveriesFromRemote(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getDeliveriesFromLocal();
    }

    @Override
    public void renderView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    public void showProgress() {
        LoadingDialog.show(this);
    }

    @Override
    public void removeProgress() {
        LoadingDialog.dismiss(this);
    }

    @Override
    public void showRetry() {
        Snackbar snackbar = Snackbar
                .make(container, R.string.label_get_delivery_error, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        snackbar.show();
        runOnUiThread(() -> swipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public void onNetworkFailure() {
        Snackbar snackbar = Snackbar
                .make(container, R.string.label_network_error, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red_500));
        snackbar.show();
        runOnUiThread(() -> swipeRefreshLayout.setRefreshing(false));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onGetDeliveriesFailure(String localizedErrMsg) {
        Log.w(TAG, localizedErrMsg);
        mAdapter.setDeliveries(null);
        mPresenter.getDeliveriesFromRemote(true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onGetDeliveriesSuccess(List<Delivery> deliveries) {
        mAdapter.setDeliveries(deliveries);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void navigateToDetailView(Delivery item) {
        if (isTabletMode) {
            if (getSupportFragmentManager().findFragmentById(R.id.detailFragment) instanceof EmptyFragment) {
                Bundle arguments = new Bundle();
                arguments.putParcelable(IntentIdentifier.DELIVERY_ITEM, item);
                DetailFragment fragment = new DetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detailFragment, fragment)
                        .commit();
            } else {
                DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detailFragment);
                detailFragment.updateDescription(item);
                detailFragment.updateCameraPosition(item);
            }
        } else {
            Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
            intent.putExtra(IntentIdentifier.DELIVERY_ITEM, item);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        removeProgress();
        super.onDestroy();
    }
}

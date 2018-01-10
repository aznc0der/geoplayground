package com.azncoder.geoplayground.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.azncoder.geoplayground.IntentIdentifier;
import com.azncoder.geoplayground.MainApplication;
import com.azncoder.geoplayground.R;
import com.azncoder.geoplayground.common.BaseFragment;
import com.azncoder.geoplayground.data.local.Delivery;
import com.azncoder.geoplayground.detail.DetailActivity;
import com.azncoder.geoplayground.detail.DetailFragment;
import com.azncoder.geoplayground.detail.EmptyFragment;
import com.azncoder.geoplayground.di.module.HomeFragmentModule;
import com.azncoder.geoplayground.home.adapter.HomeAdapter;
import com.azncoder.geoplayground.widget.LoadingDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by aznc0der on 2/1/2018.
 */

public class HomeFragment extends BaseFragment implements HomeView {
    private final String TAG = HomeActivity.class.getSimpleName();
    @BindView(R.id.container)
    ViewGroup viewContainer;
    @BindView(R.id.rv_recycle)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Inject
    HomePresenter mPresenter;
    private HomeAdapter mAdapter;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApplication) getActivity().getApplication()).getComponents().with(new HomeFragmentModule(this)).inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new HomeAdapter(it -> {
            if (((HomeActivity) getActivity()).isTabletMode()) {
                if (getActivity().getSupportFragmentManager().findFragmentById(R.id.right_fragment) instanceof EmptyFragment) {
                    initDetailView((Delivery) it, ((HomeActivity) getActivity()).isTabletMode());
                } else {
                    updateDetailView(((Delivery) it));
                }
            } else {
                navigateToDetailView((Delivery) it);
            }
        });
        recyclerView.setAdapter(mAdapter);
        swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.getDeliveriesFromRemote(false));
        mPresenter.getDeliveriesFromRemote(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getDeliveriesFromLocal();
    }

    @Override
    public void showProgress() {
        LoadingDialog.show(getActivity());
    }

    @Override
    public void removeProgress() {
        LoadingDialog.dismiss(getActivity());
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showSnack(int resId) {
        Snackbar snackbar = Snackbar
                .make(viewContainer, getString(resId), Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        snackbar.show();
    }

    @Override
    public void onGetDeliveriesFailure(String localizedErrMsg) {
        Log.w(TAG, localizedErrMsg);
        mAdapter.setDeliveries(null);
    }

    @Override
    public void onGetDeliveriesSuccess(List<Delivery> deliveries) {
        mAdapter.setDeliveries(deliveries);
    }

    @Override
    public void navigateToDetailView(Delivery delivery) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(IntentIdentifier.DELIVERY_ITEM, delivery);
        startActivity(intent);
    }

    @Override
    public void initDetailView(Delivery delivery, boolean isTabletMode) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(IntentIdentifier.DELIVERY_ITEM, delivery);
        arguments.putBoolean(IntentIdentifier.IS_TABLET_MODE, isTabletMode);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(arguments);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.right_fragment, fragment)
                .commit();
    }

    @Override
    public void updateDetailView(Delivery delivery) {
        ((DetailFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.right_fragment)).refreshUI(delivery);
    }


    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        removeProgress();
        super.onDestroy();
    }
}

package com.azncoder.geoplayground.home;

import com.azncoder.geoplayground.data.local.Delivery;

import java.util.List;

/**
 * Created by aznc0der on 1/1/2018.
 */

public interface HomeView {

    void showProgress();

    void removeProgress();

    void showRetry();

    void onNetworkFailure();

    void onGetDeliveriesFailure(String localizedErrMsg);

    void onGetDeliveriesSuccess(List<Delivery> delivery);

    void navigateToDetailView(Delivery item);
}

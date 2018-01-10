package com.azncoder.geoplayground.home;

import com.azncoder.geoplayground.data.local.Delivery;

import java.util.List;

/**
 * Created by aznc0der on 1/1/2018.
 */

public interface HomeView {

    void showProgress();

    void removeProgress();

    void showSnack(int resId);

    void onGetDeliveriesFailure(String localizedErrMsg);

    void onGetDeliveriesSuccess(List<Delivery> deliveries);

    void navigateToDetailView(Delivery delivery);

    void updateDetailView(Delivery delivery);

    void initDetailView(Delivery delivery, boolean isTabletMode);
}

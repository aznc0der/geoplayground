package com.azncoder.geoplayground.detail;

import com.azncoder.geoplayground.data.local.Delivery;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

/**
 * Created by aznc0der on 8/1/2018.
 */

public class DetailPresenter {
    private DetailView mView;
    private Delivery mDelivery;

    @Inject
    public DetailPresenter(DetailView view) {
        mView = view;
    }

    void setDelivery(Delivery delivery) {
        mDelivery = delivery;
        mView.updateDescription(delivery.getDescription(), delivery.getLocation().getAddress(), delivery.getImageUrl());
    }

    void onMapReady() {
        if (mDelivery != null) {
            mView.addMarkerToMap(new LatLng(mDelivery.getLocation().getLat(), mDelivery.getLocation().getLng()), 20f);
        }
    }
}

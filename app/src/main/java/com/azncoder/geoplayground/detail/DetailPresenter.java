package com.azncoder.geoplayground.detail;

import com.azncoder.geoplayground.data.local.Delivery;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by aznc0der on 8/1/2018.
 */

public class DetailPresenter {
    private DetailView mView;
    private Delivery mDelivery;

    public DetailPresenter(DetailView view, Delivery delivery) {
        mView = view;
        mDelivery = delivery;
    }

    void setDeliveryObject() {
        mView.updateDescription(mDelivery.getDescription(), mDelivery.getLocation().getAddress(), mDelivery.getImageUrl());
    }

    void onMapReady() {
        mView.addMarkerToMap(new LatLng(mDelivery.getLocation().getLat(), mDelivery.getLocation().getLng()), 20f);
    }
}

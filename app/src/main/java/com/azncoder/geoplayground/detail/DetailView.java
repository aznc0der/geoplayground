package com.azncoder.geoplayground.detail;

import com.azncoder.geoplayground.data.local.Delivery;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by aznc0der on 8/1/2018.
 */

public interface DetailView {
    void updateDescription(String description, String address, String imageUrl);

    void addMarkerToMap(LatLng point, float zoomLevel);

    void refreshUI(Delivery delivery);
}

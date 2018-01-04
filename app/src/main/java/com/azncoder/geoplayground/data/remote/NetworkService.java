package com.azncoder.geoplayground.data.remote;

import com.azncoder.geoplayground.data.local.Delivery;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by aznc0der on 1/1/2018.
 */

public class NetworkService {
    private final DeliveryAPI service;

    public NetworkService(DeliveryAPI service) {
        this.service = service;
    }

    public Observable<List<Delivery>> getDeliveries() {
        return service.getDeliveries();
    }
}

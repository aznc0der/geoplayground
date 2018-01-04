package com.azncoder.geoplayground.data.remote;

import com.azncoder.geoplayground.data.local.Delivery;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by aznc0der on 1/1/2018.
 */

public interface DeliveryAPI {
    @GET("deliveries")
    Observable<List<Delivery>> getDeliveries();
}

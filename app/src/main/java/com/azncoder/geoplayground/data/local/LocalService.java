package com.azncoder.geoplayground.data.local;

import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by aznc0der on 3/1/2018.
 */

public class LocalService {
    private final DeliveryDao deliveryDao;
    private final Executor executor;

    public LocalService(DeliveryDao deliveryDao, Executor executor) {
        this.deliveryDao = deliveryDao;
        this.executor = executor;
    }

    public Flowable<List<Delivery>> getDeliveries() {
        return deliveryDao.loadAll();
    }

    public void insertOrUpdateDeliveries(List<Delivery> deliveries) {
        executor.execute(() -> deliveryDao.insert(deliveries));
    }

    public void deleteAllDeliveries() {
        executor.execute(deliveryDao::deleteAllDeliveries);
    }
}

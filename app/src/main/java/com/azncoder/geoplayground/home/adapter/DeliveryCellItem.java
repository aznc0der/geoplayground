package com.azncoder.geoplayground.home.adapter;

import com.azncoder.geoplayground.data.local.Delivery;

/**
 * Created by aznc0der on 2/1/2018.
 */

public class DeliveryCellItem<T> extends CellItem {
    Delivery delivery;

    public DeliveryCellItem(T viewType, Delivery delivery) {
        super(viewType);
        this.delivery = delivery;
    }

    public Delivery getDelivery() {
        return delivery;
    }
}

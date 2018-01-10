package com.azncoder.geoplayground.data.remote;


import io.reactivex.functions.Consumer;

/**
 * Created by aznc0der on 4/1/2018.
 * Handle http error on a single class
 */

public abstract class ConsumerWrapper implements Consumer<Throwable> {
    public abstract void onServerError();

    public abstract void onNetworkError();

    @Override
    public void accept(Throwable throwable) throws Exception {

    }
}

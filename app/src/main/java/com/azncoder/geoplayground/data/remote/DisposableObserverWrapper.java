package com.azncoder.geoplayground.data.remote;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by aznc0der on 2/1/2018.
 * Handle http error on a single class, reduce boilerplate.
 */

public abstract class DisposableObserverWrapper<T> extends DisposableObserver<T> {
    public abstract void onSuccess(T t);

    public abstract void onResponseError(int code);

    public abstract void onNetworkError(String errMsg);

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
//            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            onResponseError(((HttpException) e).code());
        } else {
            onNetworkError(e.getLocalizedMessage());
        }
    }

    @Override
    public void onComplete() {
        // Do nothing.
    }
}

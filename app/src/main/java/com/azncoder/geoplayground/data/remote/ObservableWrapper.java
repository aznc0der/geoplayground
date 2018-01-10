package com.azncoder.geoplayground.data.remote;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by aznc0der on 9/1/2018.
 */

public abstract class ObservableWrapper<T> extends DisposableObserver<T> {
    public abstract void onServerError();

    public abstract void onNetworkError();

    public abstract void onSuccess(T o);

    @Override
    public void onNext(T o) {
        onSuccess(o);
        onComplete();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            int statusCode = ((HttpException) e).code();
            if (statusCode == 500) {
                onServerError();
            }
        } else {
            onNetworkError();
        }
        onComplete();
    }

    @Override
    public void onComplete() {

    }
}

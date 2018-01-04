package com.azncoder.geoplayground.home;

import com.azncoder.geoplayground.data.local.LocalService;
import com.azncoder.geoplayground.data.remote.ConsumerWrapper;
import com.azncoder.geoplayground.data.remote.NetworkService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aznc0der on 2/1/2018.
 */

public class HomePresenter {
    private final LocalService mLocalService;
    private final NetworkService mNetworkService;
    private final HomeView mView;
    private final CompositeDisposable disposableBag;

    public HomePresenter(LocalService localService, NetworkService networkService, HomeView view) {
        mLocalService = localService;
        mNetworkService = networkService;
        mView = view;
        disposableBag = new CompositeDisposable();
    }

    protected void getDeliveriesFromRemote(boolean showProgress) {
        if (showProgress) {
            mView.showProgress();
        }
        // add observable to CompositeDisposable so that it can be dispose when presenter (view model) is ready to be destroyed
        // call retrofit client on background thread and update database with response from service using Room
        disposableBag.add(Observable.just(1)
                .subscribeOn(Schedulers.computation())
                .flatMap(i -> mNetworkService.getDeliveries())
                .subscribeOn(Schedulers.io())
                .subscribe(mLocalService::insertDelivery, new ConsumerWrapper() {
                    @Override
                    public void onServerError() {
                        mView.showRetry();
                        mView.removeProgress();
                    }

                    @Override
                    public void onNetworkError() {
                        mView.onNetworkFailure();
                        mView.removeProgress();
                    }
                })
        );
    }

    protected void getDeliveriesFromLocal() {
        disposableBag.add(mLocalService.getDeliveries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deliveries -> {
                    if (deliveries != null) {
                        mView.onGetDeliveriesSuccess(deliveries);
                        mView.removeProgress();
                    } else {
                        mView.onGetDeliveriesFailure("no cached Delivery entity.");
                    }
                }, throwable -> mView.onGetDeliveriesFailure("room db error while loading Delivery entity.")));
    }

    protected void onDestroy() {
        if (!disposableBag.isDisposed()) {
            disposableBag.clear();
        }
    }
}

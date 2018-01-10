package com.azncoder.geoplayground.home;

import com.azncoder.geoplayground.R;
import com.azncoder.geoplayground.data.local.Delivery;
import com.azncoder.geoplayground.data.local.LocalService;
import com.azncoder.geoplayground.data.remote.NetworkService;
import com.azncoder.geoplayground.data.remote.ObservableWrapper;

import java.util.List;

import javax.inject.Inject;

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

    @Inject
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
        // update db with response
        disposableBag.add(mNetworkService.getDeliveries()
                .subscribeOn(Schedulers.computation())
                .subscribeWith(new ObservableWrapper<List<Delivery>>() {
                    @Override
                    public void onSuccess(List<Delivery> o) {
                        mLocalService.insertOrUpdateDeliveries(o);
                    }

                    @Override
                    public void onServerError() {
                        mView.showSnack(R.string.label_get_delivery_error);
                    }

                    @Override
                    public void onNetworkError() {
                        mView.showSnack(R.string.label_network_error);
                    }

                    @Override
                    public void onComplete() {
                        removeProgress();
                    }
                }));
    }

    private void removeProgress() {
        disposableBag.add(Observable.just(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> mView.removeProgress()));
    }

    protected void getDeliveriesFromLocal() {
        disposableBag.add(mLocalService.getDeliveries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deliveries -> {
                    if (deliveries != null) {
                        mView.onGetDeliveriesSuccess(deliveries);
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

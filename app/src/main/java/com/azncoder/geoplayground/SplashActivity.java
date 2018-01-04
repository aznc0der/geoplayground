package com.azncoder.geoplayground;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.azncoder.geoplayground.home.HomeActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aznc0der on 4/1/2018.
 */

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 600;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Observable
                .timer(SPLASH_DURATION, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .map(o -> navigateToHome())
                .subscribe();
    }

    private boolean navigateToHome() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
        return true;
    }
}

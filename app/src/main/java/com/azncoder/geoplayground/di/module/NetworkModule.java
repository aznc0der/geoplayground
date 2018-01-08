package com.azncoder.geoplayground.di.module;

import com.azncoder.geoplayground.BuildConfig;
import com.azncoder.geoplayground.data.remote.DeliveryAPI;
import com.azncoder.geoplayground.data.remote.NetworkService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aznc0der on 1/1/2018.
 */
@Module
public class NetworkModule {
    private final String baseUrl;
    private final File cacheFileDir;

    public NetworkModule(String baseUrl, File cacheFileDir) {
        this.baseUrl = baseUrl;
        this.cacheFileDir = cacheFileDir;
    }

    @Provides
    @Singleton
    Cache providesHttpCache() {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(cacheFileDir, cacheSize);
    }

    @Provides
    @Singleton
    Gson providesGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(Cache cache) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public DeliveryAPI providesAPIService(Retrofit retrofit) {
        return retrofit.create(DeliveryAPI.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public NetworkService providesNetworkService(DeliveryAPI service) {
        return new NetworkService(service);
    }
}

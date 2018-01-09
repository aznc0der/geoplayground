package com.azncoder.geoplayground;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by aznc0der on 1/1/2018.
 */

public class Environment {
    @StringDef({PROD, STAG, DEV, LOCAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BaseUrlDef {}

    public static final String PROD = "http://192.168.57.1:8080";
    public static final String STAG = "http://192.168.57.1:8080";
    public static final String DEV = "http://192.168.57.1:8080";
    public static final String LOCAL = "http://localhost:8080";

    private String baseUrl;

    public Environment(String flavor) {
        switch (flavor) {
            case "production": {
                setBaseUrl(PROD);
                break;
            }
            case "staging": {
                setBaseUrl(STAG);
                break;
            }
            case "dev": {
                setBaseUrl(DEV);
                break;
            }
            case "local":
            default: {
                setBaseUrl(LOCAL);
                break;
            }
        }
    }

    public void setBaseUrl(@BaseUrlDef String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}

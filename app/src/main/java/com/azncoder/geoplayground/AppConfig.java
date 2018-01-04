package com.azncoder.geoplayground;

/**
 * Created by aznc0der on 1/1/2018.
 */

public class AppConfig {
    private class Production {
        private static final String REST_API_BASE_URL = "http://192.168.57.1:8080/";
    }

    private class Staging {
        private static final String REST_API_BASE_URL = "http://192.168.57.1:8080/";
    }

    private class Dev {
        private static final String REST_API_BASE_URL = "http://192.168.57.1:8080/";
    }

    private class Local {
        private static final String REST_API_BASE_URL = "http://192.168.57.1:8080/";
    }

    public static final String REST_API_BASE_URL;

    static {
        switch (BuildConfig.FLAVOR) {
            default: {
                REST_API_BASE_URL = Local.REST_API_BASE_URL;
            }
        }
    }
}

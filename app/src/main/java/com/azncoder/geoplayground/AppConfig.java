package com.azncoder.geoplayground;

/**
 * Created by aznc0der on 1/1/2018.
 */

public class AppConfig {
    private class Prod {
        private static final String REST_API_BASE_URL = "http://localhost:8080/";
    }

    private class Stag {
        private static final String REST_API_BASE_URL = "http://localhost:8080/";
    }

    private class Dev {
        private static final String REST_API_BASE_URL = "http://localhost:8080/";
    }

    private class Local {
        private static final String REST_API_BASE_URL = "http://localhost:8080/";
    }

    public static final String REST_API_BASE_URL;

    static {
        switch (BuildConfig.FLAVOR) {
            case "production": {
                REST_API_BASE_URL = Prod.REST_API_BASE_URL;
                break;
            }
            case "staging": {
                REST_API_BASE_URL = Stag.REST_API_BASE_URL;
                break;
            }
            case "dev": {
                REST_API_BASE_URL = Dev.REST_API_BASE_URL;
                break;
            }
            case "local":
            default: {
                REST_API_BASE_URL = Local.REST_API_BASE_URL;
                break;
            }
        }
    }
}

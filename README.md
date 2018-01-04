### App Overview
This app is a demo showcasing a way of implementing offline caching approach with RXJava2, Retrofit2 and RoomDB. <br />
Payload is stored in room then retrieve from it to populate the UI. <br />
A background task is created to call remote service to get new data then update the database after.

### Configuration
Please change the REST_API_BASE_URL to the appropriate server URL in AppConfig.java. <br />
Choose your desire build variants in Androud Studio or use `./gradlew assemble` to build all flavors. (Production, Staging, Development and Localhost)

### Libraries Used
* facebook/Fresco
* squareup/okhttp3
* squareup/retrofit2
* jakewharton/butterknife
* reactivex/rxjava
* reactivex/rxandroid
* google/dagger2
* google/gson
* google/play-service-maps
* google/android-map-utils
* google/room

### Architecture Overview
* View -> Holds the contract describing user actions and UI updates from presenter.
* Presenter -> Presenter implementation handling user actions.
* Model -> The data provided to show in the view. Network and DB modules are injected with Dagger.
* Activity/Fragment -> View implementation + Presenter interaction.

### Credit
Icon made by [Dimitry Miroliubov](https://www.flaticon.com/authors/dimitry-miroliubov) from [www.flaticon.com](www.flaticon.com)

package com.framgia.soundcloud;

import android.app.Application;
import com.framgia.soundcloud.data.source.remote.service.NetworkModule;

/**
 * Created by anh on 25/10/2017.
 */

public class App extends Application {
    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppComponent getComponent() {
        if (mComponent == null) {
            mComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(getApplicationContext()))
                    .networkModule(new NetworkModule())
                    .build();
        }
        return mComponent;
    }
}

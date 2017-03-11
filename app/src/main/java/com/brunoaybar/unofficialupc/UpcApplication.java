package com.brunoaybar.unofficialupc;

import android.app.Application;

import com.brunoaybar.unofficialupc.analytics.AnalyticsManager;
import com.brunoaybar.unofficialupc.analytics.AppRemoteConfig;

import dagger.Component;

/**
 * Created by brunoaybar on 21/10/2016.
 */

public class UpcApplication extends Application {

    private static UpcApplication INSTANCE;
    public static UpcApplication get(){
        return INSTANCE;
    }

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        AnalyticsManager.setup(this);
        AppRemoteConfig.setup();
    }

    public static AppComponent getComponent(){
        return get().component;
    }

}

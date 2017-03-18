package com.brunoaybar.unofficialupc.injection;

import android.app.Application;
import android.content.Context;

import com.brunoaybar.unofficialupc.analytics.AppRemoteConfig;
import com.brunoaybar.unofficialupc.utils.AndroidConnectionManager;
import com.brunoaybar.unofficialupc.utils.DateProviderImpl;
import com.brunoaybar.unofficialupc.utils.interfaces.DateProvider;
import com.brunoaybar.unofficialupc.utils.interfaces.InternetVerifier;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by brunoaybar on 11/03/2017.
 */

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton
    public Context provideContext(){
        return application;
    }

    @Provides @Singleton
    public DateProvider provideDateProvider(){
        return new DateProviderImpl();
    }

    @Provides @Singleton
    public InternetVerifier provideInternetVerifier(){
        return new AndroidConnectionManager(application);
    }

    @Provides @Singleton
    public AppRemoteConfig provideRemoteConfig(){
        return AppRemoteConfig.getInstance();
    }

}

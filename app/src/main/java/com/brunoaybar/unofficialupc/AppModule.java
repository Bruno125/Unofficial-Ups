package com.brunoaybar.unofficialupc;

import android.app.Application;
import android.content.Context;

import com.brunoaybar.unofficialupc.utils.DateProviderImpl;
import com.brunoaybar.unofficialupc.utils.interfaces.DateProvider;

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

}

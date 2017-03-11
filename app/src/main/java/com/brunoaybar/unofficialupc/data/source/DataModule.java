package com.brunoaybar.unofficialupc.data.source;

import android.content.Context;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.source.interfaces.UpcDao;
import com.brunoaybar.unofficialupc.data.source.interfaces.UpcService;
import com.brunoaybar.unofficialupc.data.source.preferences.UserPreferencesDataSource;
import com.brunoaybar.unofficialupc.data.source.remote.UpcServiceDataSource;
import com.brunoaybar.unofficialupc.utils.interfaces.DateProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by brunoaybar on 11/03/2017.
 */

@Module
public class DataModule {

    @Inject
    Context context;
    @Inject
    DateProvider dateProvider;

    public DataModule(){
        UpcApplication.getComponent().inject(this);
    }

    @Provides @Singleton
    public UpcDao provideDao(){
        return new UserPreferencesDataSource(context,dateProvider);
    }

    @Provides @Singleton
    public UpcService provideService(){
        return new UpcServiceDataSource();
    }
}

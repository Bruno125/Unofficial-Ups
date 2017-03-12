package com.brunoaybar.unofficialupc.injection;

import com.brunoaybar.unofficialupc.data.models.errors.NoInternetException;
import com.brunoaybar.unofficialupc.data.source.injection.DataModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by brunoaybar on 11/03/2017.
 */

@Singleton
@Component( modules = { AppModule.class})
public interface AppComponent extends BaseAppComponent{
}

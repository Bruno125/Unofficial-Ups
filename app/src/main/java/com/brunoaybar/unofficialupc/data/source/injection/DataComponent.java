package com.brunoaybar.unofficialupc.data.source.injection;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by brunoaybar on 11/03/2017.
 */

@Singleton
@Component(modules = { DataModule.class, RepositoryModule.class })
public interface DataComponent extends BaseDataComponent{
}

package com.brunoaybar.unofficialupc.data.source;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by brunoaybar on 11/03/2017.
 */

@Singleton
@Component(modules = { DataModule.class })
public interface DataComponent {
    void inject(UpcRepository target);
}

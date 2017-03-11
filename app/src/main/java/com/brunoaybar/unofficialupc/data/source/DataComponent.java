package com.brunoaybar.unofficialupc.data.source;

import com.brunoaybar.unofficialupc.data.repository.impl.UpcLoginRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.UpcSessionRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by brunoaybar on 11/03/2017.
 */

@Singleton
@Component(modules = { DataModule.class, RepositoryModule.class })
public interface DataComponent {
    void inject(UpcRepository target);
    void inject(UpcSessionRepository target);
    void inject(UpcLoginRepository target);
}

package com.brunoaybar.unofficialupc.data.source;

import com.brunoaybar.unofficialupc.data.repository.impl.UpcLoginRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.UpcSessionRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.UpcUserRepository;
import com.brunoaybar.unofficialupc.data.source.remote.UpcServiceDataSource;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by brunoaybar on 11/03/2017.
 */

@Singleton
@Component(modules = { DataModule.class, RepositoryModule.class })
public interface DataComponent {
    //User repos
    void inject(UpcSessionRepository target);
    void inject(UpcLoginRepository target);
    void inject(UpcUserRepository target);
    void inject (UpcServiceDataSource target);
}

package com.brunoaybar.unofficialupc.data.source.injection;

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
public interface DataComponent extends BaseDataComponent{
}

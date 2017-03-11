package com.brunoaybar.unofficialupc.data.source;

import com.brunoaybar.unofficialupc.data.repository.LoginRepository;
import com.brunoaybar.unofficialupc.data.repository.SessionRepository;
import com.brunoaybar.unofficialupc.data.repository.UserRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.UpcLoginRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.UpcSessionRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.UpcUserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by brunoaybar on 11/03/2017.
 */

@Module
public class RepositoryModule {

    @Provides @Singleton
    public SessionRepository provideSessionRepo(){
        return new UpcSessionRepository();
    }

    @Provides @Singleton
    public LoginRepository provideLoginRepo(){
        return new UpcLoginRepository();
    }

    @Provides @Singleton
    public UserRepository provideUserRepo(){
        return new UpcUserRepository();
    }

}

package com.brunoaybar.unofficialupc.data.source.injection;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.repository.LoginRepository;
import com.brunoaybar.unofficialupc.data.repository.SessionRepository;
import com.brunoaybar.unofficialupc.data.repository.UniversityInfoRepository;
import com.brunoaybar.unofficialupc.data.repository.UserRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.demo.DemoLoginRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.demo.DemoSessionRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.demo.DemoUniversityInfoRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.demo.DemoUserRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.upc.UpcLoginRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.upc.UpcSessionRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.upc.UpcUniversityInfoRepository;
import com.brunoaybar.unofficialupc.data.repository.impl.upc.UpcUserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides @Singleton
    public SessionRepository provideSessionRepo(){
        return new DemoSessionRepository(UpcApplication.getDataComponent());
    }

    @Provides @Singleton
    public LoginRepository provideLoginRepo(){
        return new DemoLoginRepository(UpcApplication.getDataComponent());
    }

    @Provides @Singleton
    public UserRepository provideUserRepo(){
        return new DemoUserRepository();
    }

    @Provides @Singleton
    public UniversityInfoRepository provideUniversityInfoRepo(){
        return new DemoUniversityInfoRepository();
    }

}

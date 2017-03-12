package com.brunoaybar.unofficialupc.data.repository.impl;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.models.User;
import com.brunoaybar.unofficialupc.data.repository.LoginRepository;
import com.brunoaybar.unofficialupc.data.repository.SessionRepository;
import com.brunoaybar.unofficialupc.data.source.injection.BaseDataComponent;
import com.brunoaybar.unofficialupc.data.source.injection.DataComponent;
import com.brunoaybar.unofficialupc.data.source.interfaces.ApplicationDao;
import com.brunoaybar.unofficialupc.data.source.interfaces.RemoteSource;
import com.brunoaybar.unofficialupc.utils.CryptoUtils;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by brunoaybar on 11/03/2017.
 */

public class UpcLoginRepository implements LoginRepository {
    @Inject public ApplicationDao applicationDao;
    @Inject public RemoteSource remoteSource;
    @Inject public SessionRepository mSessionRepository;

    public UpcLoginRepository(BaseDataComponent component){
        component.inject(this);
    }

    public Observable<Boolean> verifyUserSession(){
        return mSessionRepository.getSession().map(User::hasValidSession);
    }

    public Observable<Boolean> login(String userCode, String password, boolean rememberCredentials){
        return loginWithEncryptedPass(userCode, CryptoUtils.encryptPassword(password),rememberCredentials);
    }

    private Observable<Boolean> loginWithEncryptedPass(String userCode, String encryptedPassword,boolean rememberCredentials){
        applicationDao.setUserAgreeToSaveSession(rememberCredentials);
        return remoteSource.login(userCode,encryptedPassword)
                .map(applicationDao::saveUser)
                .map(User::hasValidSession);
    }
}

package com.brunoaybar.unofficialupc.data.repository.impl;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.models.User;
import com.brunoaybar.unofficialupc.data.repository.LoginRepository;
import com.brunoaybar.unofficialupc.data.repository.SessionRepository;
import com.brunoaybar.unofficialupc.data.source.interfaces.UpcDao;
import com.brunoaybar.unofficialupc.data.source.interfaces.UpcService;
import com.brunoaybar.unofficialupc.data.source.preferences.UserPreferencesDataSource;
import com.brunoaybar.unofficialupc.data.source.remote.UpcServiceDataSource;
import com.brunoaybar.unofficialupc.utils.CryptoUtils;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by brunoaybar on 11/03/2017.
 */

public class UpcLoginRepository implements LoginRepository {
    @Inject
    UpcDao mDataSource;
    @Inject
    UpcService mRemoteDataSource;
    @Inject
    SessionRepository mSessionRepository;

    public UpcLoginRepository(){
        UpcApplication.getDataComponent().inject(this);
    }

    public Observable<Boolean> verifyUserSession(){
        return mSessionRepository.getSession().map(User::hasValidSession);
    }

    public Observable<Boolean> login(String userCode, String password, boolean rememberCredentials){
        return loginWithEncryptedPass(userCode, CryptoUtils.encryptPassword(password),rememberCredentials);
    }

    private Observable<Boolean> loginWithEncryptedPass(String userCode, String encryptedPassword,boolean rememberCredentials){
        mDataSource.setUserAgreeToSaveSession(rememberCredentials);
        return mRemoteDataSource.login(userCode,encryptedPassword)
                .map(mDataSource::saveUser)
                .map(User::hasValidSession);
    }
}

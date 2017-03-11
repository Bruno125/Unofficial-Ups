package com.brunoaybar.unofficialupc.modules.auth;

import android.text.TextUtils;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.repository.LoginRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * View Model for the Login Activity.
 */

public class LoginViewModel {

    @Inject
    LoginRepository mDataModel;

    public LoginViewModel(){
        UpcApplication.getViewModelsComponent().inject(this);
    }

    public Observable<Boolean> verifyState(){
        return Observable.create(subscriber -> {
            mDataModel.verifyUserSession().subscribe(
                    subscriber::onNext,
                    error -> {  subscriber.onNext(false); }
            );
        });
    }

    public Observable<Boolean> login(String userCode, String password, boolean rememberCredentials){
        return mDataModel.login(userCode,password,rememberCredentials);
    }

    public boolean userIsValid(String userCode){
        return !TextUtils.isEmpty(userCode) && userCode.matches("[uU][0-9]{9}");
    }

    public boolean passwordIsValid(String password){
        return !TextUtils.isEmpty(password) && password.length()>5;
    }
}

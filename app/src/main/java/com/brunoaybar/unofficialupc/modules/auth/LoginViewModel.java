package com.brunoaybar.unofficialupc.modules.auth;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.brunoaybar.unofficialupc.data.models.User;
import com.brunoaybar.unofficialupc.data.source.preferences.UserPreferencesDataSource;
import com.brunoaybar.unofficialupc.data.source.remote.UpcServiceDataSource;
import com.brunoaybar.unofficialupc.utils.CryptoUtils;

import rx.Observable;

/**
 * View Model for the Login Activity.
 */

public class LoginViewModel {

    @NonNull private final LoginDataModel mDataModel;

    public LoginViewModel(@NonNull final LoginDataModel loginDataModel){
        mDataModel = loginDataModel;
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

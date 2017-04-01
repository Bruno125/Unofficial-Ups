package com.brunoaybar.unofficialupc.modules.auth;

import android.text.TextUtils;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.repository.LoginRepository;
import com.brunoaybar.unofficialupc.utils.Utils;
import com.brunoaybar.unofficialupc.utils.interfaces.StringProvider;

import javax.inject.Inject;

import rx.Observable;

/**
 * View Model for the Login Activity.
 */

public class LoginViewModel {

    @Inject
    LoginRepository mDataModel;

    @Inject
    StringProvider stringProvider;

    public LoginViewModel(){
        UpcApplication.getViewModelsComponent().inject(this);
    }

    public Observable<Boolean> verifyState(){
        return Observable.create(subscriber -> {
            mDataModel.verifyUserSession().subscribe(
                    subscriber::onNext,
                    error -> subscriber.onNext(false)
            );
        });
    }

    public Observable<Boolean> login(String userCode, String password, boolean rememberCredentials){
        return Observable.create( s -> mDataModel.login(userCode,password,rememberCredentials)
                .subscribe(s::onNext, e -> {
                    s.onError(Utils.getError(e,stringProvider.getString(R.string.error_login)));
                }));
    }

    public boolean userIsValid(String userCode){
        return !TextUtils.isEmpty(userCode) && userCode.matches("[uU][0-9]{9}");
    }

    public boolean passwordIsValid(String password){
        return !TextUtils.isEmpty(password) && password.length()>5;
    }
}

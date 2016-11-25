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

    @NonNull private final UserPreferencesDataSource mDataSource;
    @NonNull private final UpcServiceDataSource mRemoteDataSource;


    public LoginViewModel(@NonNull final UserPreferencesDataSource dataSource,
                          @NonNull final UpcServiceDataSource remoteDataSource){
        mDataSource = dataSource;
        mRemoteDataSource = remoteDataSource;
    }

    @NonNull
    public Observable<Boolean> verifyUserSession(){

        return Observable.create(subscriber -> mDataSource.userAgreeToSaveSession().subscribe(agreed -> {

            //Only proceed if user agreed to save session
            if(!agreed){
                mDataSource.removeUser();
                subscriber.onError(new Throwable());
                return;
            }

            // Get userCode, token, and password from preferences
            Observable.zip(
                    mDataSource.getToken(),
                    mDataSource.getUserCode(),
                    mDataSource.getSavedPassword(), User::new)
                    .subscribe(tempUser -> {
                        Observable<Boolean> verifyResponse = null;
                        // Login again using credentials
                        if(tempUser.hasValidCredentials()){
                            verifyResponse = loginWithEncryptedPass(tempUser.getUserCode(),tempUser.getSavedPassword(),true);
                        }
                        // Check if we have a token available
                        else if(tempUser.hasValidSession()){
                            //Verify if token is still active
                            verifyResponse = mRemoteDataSource.validateToken(tempUser.getUserCode(),tempUser.getToken());
                        }

                        //We had a way of verifying the session
                        if (verifyResponse!=null){
                            verifyResponse.subscribe(valid -> {
                                if(valid) {
                                    subscriber.onNext(true);
                                    subscriber.onCompleted();
                                }else
                                    subscriber.onError(new Throwable());
                            }, throwable -> {
                                subscriber.onError(new Throwable());
                            });
                            //Verification failed
                        }else {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                            subscriber.onError(new Throwable());
                        }
                    },subscriber::onError);
        }));
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

    public boolean userIsValid(String userCode){
        return !TextUtils.isEmpty(userCode) && userCode.matches("[uU][0-9]{9}");
    }

    public boolean passwordIsValid(String password){
        return !TextUtils.isEmpty(password) && password.length()>5;
    }
}

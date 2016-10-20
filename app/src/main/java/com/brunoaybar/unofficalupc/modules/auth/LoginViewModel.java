package com.brunoaybar.unofficalupc.modules.auth;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.text.TextUtils;

import com.brunoaybar.unofficalupc.data.models.User;
import com.brunoaybar.unofficalupc.data.source.preferences.UserPreferencesDataSource;
import com.brunoaybar.unofficalupc.data.source.remote.UpcServiceDataSource;
import com.brunoaybar.unofficalupc.utils.CryptoUtils;

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

        return Observable.create(subscriber ->
                // Get userCode, token, and password from preferences
                Observable.zip(
                        mDataSource.getToken(),
                        mDataSource.getUserCode(),
                        mDataSource.getSavedPassword(), User::new)
                        .subscribe(tempUser -> {
                            Observable<Boolean> verifyResponse = null;
                            // Login again using credentials
                            if(tempUser.hasValidCredentials()){
                                verifyResponse = loginWithEncryptedPass(tempUser.getUserCode(),tempUser.getSavedPassword());
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
                        }));
    }

    public Observable<Boolean> login(String userCode, String password){
        return loginWithEncryptedPass(userCode, CryptoUtils.encryptPassword(password));
    }

    private Observable<Boolean> loginWithEncryptedPass(String userCode, String encryptedPassword){
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

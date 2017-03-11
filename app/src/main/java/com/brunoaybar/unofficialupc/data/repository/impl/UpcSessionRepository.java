package com.brunoaybar.unofficialupc.data.repository.impl;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.models.User;
import com.brunoaybar.unofficialupc.data.repository.SessionRepository;
import com.brunoaybar.unofficialupc.data.source.interfaces.UpcDao;
import com.brunoaybar.unofficialupc.data.source.interfaces.UpcService;

import java.util.Date;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by brunoaybar on 11/03/2017.
 */

public class UpcSessionRepository implements SessionRepository {

    @Inject
    UpcDao applicationDao;
    @Inject
    UpcService mServiceSource;

    public UpcSessionRepository() {
        UpcApplication.getDataComponent().inject(this);
    }

    @Override
    public Observable<User> getSession() {
        return Observable.zip(getToken(), getUserCode(), getSavedPassword(), User::new)
                .subscribeOn(Schedulers.io());
    }

    private static final int TOKEN_LIFETIME = 5 * 60 * 1000; //5 minutes
    private Observable<Boolean> tokenIsStillValid(){
        return applicationDao.getLastUpdateTime()
                .map(lastUpdate -> {
                    long millisecondsElapsed = new Date().getTime() - lastUpdate.getTime();
                    return millisecondsElapsed < TOKEN_LIFETIME;
                });
    }

    private Observable<String> getUserCode(){
        return applicationDao.getUserCode();
    }
    private Observable<String> getSavedPassword(){
        return applicationDao.getSavedPassword();
    }
    private Observable<String> getToken(){
        return Observable.create(subscriber -> {
            //Evaluate if token is still valid
            tokenIsStillValid().subscribe(valid ->{
                if(valid){ //If valid, get token from preferences and return it
                    applicationDao.getToken().subscribe(token ->{
                        subscriber.onNext(token);
                        subscriber.onCompleted();
                    },subscriber::onError);
                }else{ //If not, check if user agreed to save it's session
                    applicationDao.userAgreeToSaveSession().subscribe(agreed -> {
                        //User didn't want to save session, so there's nothing we can do
                        if(!agreed){
                            subscriber.onError(new Throwable());
                            return;
                        }
                        //But if he/she did, we can try to login to get a new token
                        Observable.zip(applicationDao.getUserCode(),
                                applicationDao.getSavedPassword(),
                                (userCode,password) -> new User(null,userCode,password))
                                .subscribe(info -> {
                                    if (!info.hasValidCredentials()){
                                        subscriber.onError(new Throwable());
                                        return;
                                    }

                                    mServiceSource.login(info.getUserCode(),info.getSavedPassword()) //Do login
                                            .map(applicationDao::saveUser) //Save user
                                            .subscribe(user -> {
                                                subscriber.onNext(user.getToken()); //Return token
                                            }, subscriber::onError); //In case there is any error
                                },subscriber::onError);
                    }, subscriber::onError);
                }
            },subscriber::onError);
        });
    }

    @Override
    public Observable<User> saveSession(User user) {
        return Observable.just(applicationDao.saveUser(user));
    }

    @Override
    public Observable<Boolean> logout() {
        applicationDao.removeUser();
        return Observable.just(true);
    }
}

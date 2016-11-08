package com.brunoaybar.unofficalupc.data.source;

import android.support.annotation.NonNull;

import com.brunoaybar.unofficalupc.data.models.Absence;
import com.brunoaybar.unofficalupc.data.models.Course;
import com.brunoaybar.unofficalupc.data.models.Timetable;
import com.brunoaybar.unofficalupc.data.models.User;
import com.brunoaybar.unofficalupc.data.source.preferences.UserPreferencesDataSource;
import com.brunoaybar.unofficalupc.data.source.remote.UpcServiceDataSource;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.schedulers.Schedulers;

/**
 * Created by brunoaybar on 15/10/2016.
 */

public class UpcRepository {

    private UserPreferencesDataSource mPreferencesSource;

    private UpcServiceDataSource mServiceSource;


    public UpcRepository(@NonNull UserPreferencesDataSource preferencesDataSource,
                         @NonNull UpcServiceDataSource serviceDataSource){
        mPreferencesSource = preferencesDataSource;
        mServiceSource = serviceDataSource;
    }


    public Observable<String> getUserCode(){
        return mPreferencesSource.getUserCode();
    }

    public Observable<String> getSavedPassword(){
        return mPreferencesSource.getSavedPassword();
    }

    private static final int TOKEN_LIFETIME = 5 * 60 * 1000; //5 minutes
    private Observable<Boolean> tokenIsStillValid(){
        return mPreferencesSource.getLastUpdateTime()
                .map(lastUpdate -> {
                    long millisecondsEllapsed = new Date().getTime() - lastUpdate.getTime();
                    return millisecondsEllapsed < TOKEN_LIFETIME;
                });
    }

    public Observable<String> getToken(){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //Evaluate if token is still valid
                tokenIsStillValid().subscribe(valid ->{
                    if(valid){ //If valid, get token from preferences and return it
                        mPreferencesSource.getToken().subscribe(token ->{
                            subscriber.onNext(token);
                            subscriber.onCompleted();
                        });
                    }else{ //If not, check if user agreed to save it's session
                        mPreferencesSource.userAgreeToSaveSession().subscribe( agreed -> {
                            //User didn't want to save session, so there's nothing we can do
                            if(!agreed){
                                subscriber.onError(new Throwable());
                                return;
                            }
                            //But if he/she did, we can try to login to get a new token
                            Observable.zip(mPreferencesSource.getUserCode(),
                                    mPreferencesSource.getSavedPassword(),
                                    (userCode,password) -> new User(null,userCode,password))
                                    .subscribe(info -> {
                                        mServiceSource.login(info.getUserCode(),info.getSavedPassword()) //Do login
                                                .map(mPreferencesSource::saveUser) //Save user
                                                .subscribe(user -> {
                                                    subscriber.onNext(user.getToken()); //Return token
                                                }, subscriber::onError); //In case there is any error
                                    });

                        });
                    }
                });
            }
        });
    }

    public Observable<User> getSession(){
        return Observable.zip(
                getToken().subscribeOn(Schedulers.immediate()),
                getUserCode().subscribeOn(Schedulers.immediate()),
                (token,userCode) -> new User(token,userCode,null))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> login(String userCode, String password){
        return mServiceSource.login(userCode,password) //Do login
                .map(mPreferencesSource::saveUser) //Save user
                .map(User::hasValidSession);
    }

    public Observable<Timetable> getTimeTable(String userCode, String token){
        return mServiceSource.getTimeTable(userCode,token);
    }

    public Observable<List<Course>> getCourses(User user){
        return mServiceSource.getCourses(user.getUserCode(),user.getToken());
    }

    public Observable<Course> getCourseDetail(User user, String courseCode){
        return mServiceSource.getCourseDetail(courseCode,user.getUserCode(),user.getToken());
    }

    public Observable<List<Absence>> getAbsences(User user){
        return mServiceSource.getAbsences(user.getUserCode(),user.getToken());
    }
}

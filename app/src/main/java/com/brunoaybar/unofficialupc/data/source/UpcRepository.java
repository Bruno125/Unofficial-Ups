package com.brunoaybar.unofficialupc.data.source;

import android.support.annotation.NonNull;

import com.brunoaybar.unofficialupc.data.models.Absence;
import com.brunoaybar.unofficialupc.data.models.Classmate;
import com.brunoaybar.unofficialupc.data.models.Course;
import com.brunoaybar.unofficialupc.data.models.Timetable;
import com.brunoaybar.unofficialupc.data.models.User;
import com.brunoaybar.unofficialupc.data.source.preferences.UserPreferencesDataSource;
import com.brunoaybar.unofficialupc.data.source.remote.UpcServiceDataSource;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
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
                    long millisecondsElapsed = new Date().getTime() - lastUpdate.getTime();
                    return millisecondsElapsed < TOKEN_LIFETIME;
                });
    }

    public Observable<String> getToken(){
        return Observable.create(subscriber -> {
                //Evaluate if token is still valid
                tokenIsStillValid().subscribe(valid ->{
                    if(valid){ //If valid, get token from preferences and return it
                        mPreferencesSource.getToken().subscribe(token ->{
                            subscriber.onNext(token);
                            subscriber.onCompleted();
                        },subscriber::onError);
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
                                    },subscriber::onError);
                        }, subscriber::onError);
                    }
                },subscriber::onError);
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

    public Observable<Boolean> logout(){
        mPreferencesSource.removeUser();
        return Observable.just(true);
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

    public Observable<List<Classmate>> getClassmates(User user, String courseCode){
        return mServiceSource.getClassmates(courseCode,user);
    }

}

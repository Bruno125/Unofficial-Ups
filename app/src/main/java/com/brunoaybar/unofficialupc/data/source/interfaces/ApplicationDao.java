package com.brunoaybar.unofficialupc.data.source.interfaces;

import com.brunoaybar.unofficialupc.data.models.User;

import java.util.Date;

import rx.Observable;

/**
 * Created by brunoaybar on 11/03/2017.
 */

public interface ApplicationDao {
    //Read
    Observable<String> getToken();
    Observable<String> getUserCode();
    Observable<String> getSavedPassword();
    Observable<Date> getLastUpdateTime();
    Observable<Boolean> userAgreeToSaveSession();

    //Write
    void setUserAgreeToSaveSession(boolean value);
    User saveUser(User user);
    void removeUser();

}

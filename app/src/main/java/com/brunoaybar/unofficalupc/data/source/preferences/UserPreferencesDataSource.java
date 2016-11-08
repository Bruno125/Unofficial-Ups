package com.brunoaybar.unofficalupc.data.source.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.brunoaybar.unofficalupc.data.models.User;
import com.brunoaybar.unofficalupc.utils.CryptoUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

/**
 * Created by brunoaybar on 14/10/2016.
 */

public class UserPreferencesDataSource {

    private static final String KEY_LAST_UPDATE = "key_last_update";
    private static final String KEY_TOKEN = "key_token";
    private static final String KEY_USER_CODE = "key_user_code";
    private static final String KEY_PASS = "key_pass";

    @NonNull private Context mContext;

    public UserPreferencesDataSource(@NonNull Context context){
        mContext = context;
    }

    public Observable<Boolean> userAgreeToSaveSession(){
        return Observable.just(true);
    }

    public Observable<String> getToken(){
        String token = PreferenceUtils.getString(mContext,KEY_TOKEN);
        return Observable.just(token);
    }

    public Observable<String> getUserCode(){
        String userCode = PreferenceUtils.getString(mContext,KEY_USER_CODE);
        return Observable.just(userCode);
    }

    public Observable<String> getSavedPassword(){
        String userCode = PreferenceUtils.getString(mContext,KEY_PASS);
        return Observable.just(userCode);
    }

    public Observable<User> getSession(){
        return Observable.zip(getToken(),getUserCode(),getSavedPassword(),(User::new));
    }

    private static final SimpleDateFormat updateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
    public Observable<Date> getLastUpdateTime(){
        String timestamp = PreferenceUtils.getString(mContext,KEY_LAST_UPDATE);
        try {
            if(TextUtils.isEmpty(timestamp))
                return Observable.just(new Date(0));
            else {
                Date lastUpdateTime = updateFormat.parse(timestamp);
                return Observable.just(lastUpdateTime);
            }
        } catch (ParseException e) {
            return Observable.error(Exceptions.propagate(e));
        }
    }

    public User saveUser(User user){
        //Save user info
        PreferenceUtils.saveString(mContext,KEY_TOKEN,user.getToken());
        PreferenceUtils.saveString(mContext,KEY_USER_CODE,user.getUserCode());
        PreferenceUtils.saveString(mContext,KEY_PASS,user.getSavedPassword());

        //Update last update timestamp
        String currentDateAndTime = updateFormat.format(new Date());
        PreferenceUtils.saveString(mContext,KEY_LAST_UPDATE,currentDateAndTime);

        //Return the user instance
        return user;
    }

    public void removeUser(){
        //Save user info
        PreferenceUtils.saveString(mContext,KEY_TOKEN,null);
        PreferenceUtils.saveString(mContext,KEY_USER_CODE,null);
        PreferenceUtils.saveString(mContext,KEY_PASS,null);

        //Update last update timestamp
        String currentDateAndTime = updateFormat.format(new Date());
        PreferenceUtils.saveString(mContext,KEY_LAST_UPDATE,currentDateAndTime);
    }

}

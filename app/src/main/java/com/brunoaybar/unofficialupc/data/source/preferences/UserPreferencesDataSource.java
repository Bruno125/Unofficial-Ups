package com.brunoaybar.unofficialupc.data.source.preferences;

import android.content.Context;
import android.support.annotation.NonNull;

import com.brunoaybar.unofficialupc.data.models.User;
import com.brunoaybar.unofficialupc.data.source.interfaces.ApplicationDao;
import com.brunoaybar.unofficialupc.utils.DateProviderImpl;
import com.brunoaybar.unofficialupc.utils.Utils;
import com.brunoaybar.unofficialupc.utils.interfaces.DateProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import rx.Observable;
import rx.exceptions.Exceptions;

public class UserPreferencesDataSource implements ApplicationDao {

    private static final String KEY_SAVE_SESSION = "key_save_session";
    private static final String KEY_LAST_UPDATE = "key_last_update";
    private static final String KEY_TOKEN = "key_token";
    private static final String KEY_USER_CODE = "key_user_code";
    private static final String KEY_PASS = "key_pass";

    @NonNull private Context mContext;
    @NonNull private DateProvider mDateProvider;

    public UserPreferencesDataSource(@NonNull Context context){
        this(context,new DateProviderImpl());
    }

    public UserPreferencesDataSource(@NonNull Context context,@NonNull DateProvider dateProvider){
        mContext = context;
        mDateProvider = dateProvider;
    }

    public Observable<Boolean> userAgreeToSaveSession(){
        return Observable.just(PreferenceUtils.getBool(mContext,KEY_SAVE_SESSION,false));
    }

    public void setUserAgreeToSaveSession(boolean value){
        PreferenceUtils.saveBoolean(mContext,KEY_SAVE_SESSION,value);
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

    private static final SimpleDateFormat updateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
    public Observable<Date> getLastUpdateTime(){
        String timestamp = PreferenceUtils.getString(mContext,KEY_LAST_UPDATE);
        try {
            if(Utils.isEmpty(timestamp))
                return Observable.just(mDateProvider.getNever());
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
        String currentDateAndTime = updateFormat.format(mDateProvider.getNow());
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
        String currentDateAndTime = updateFormat.format(mDateProvider.getNow());
        PreferenceUtils.saveString(mContext,KEY_LAST_UPDATE,currentDateAndTime);
    }
}

package com.brunoaybar.unofficalupc.data.source.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.brunoaybar.unofficalupc.data.models.User;
import com.brunoaybar.unofficalupc.utils.CryptoUtils;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by brunoaybar on 14/10/2016.
 */

public class UserPreferencesDataSource {

    private static final String KEY_TOKEN = "key_token";
    private static final String KEY_USER_CODE = "key_user_code";
    private static final String KEY_PASS = "key_pass";

    @NonNull private Context mContext;

    public UserPreferencesDataSource(@NonNull Context context){
        mContext = context;
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

    public User saveUser(User user,String password){
        user.setSavedPassword(password);
        PreferenceUtils.saveString(mContext,KEY_TOKEN,user.getToken());
        PreferenceUtils.saveString(mContext,KEY_USER_CODE,user.getUserCode());
        PreferenceUtils.saveString(mContext,KEY_PASS,user.getSavedPassword());
        return user;
    }
}

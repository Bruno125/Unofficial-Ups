package com.brunoaybar.unofficialupc.analytics;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.brunoaybar.unofficialupc.BuildConfig;
import com.brunoaybar.unofficialupc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

/**
 * Created by brunoaybar on 25/11/2016.
 */

public class AppRemoteConfig {

    private static AppRemoteConfig INSTANCE = new AppRemoteConfig();
    private AppRemoteConfig(){}
    public static AppRemoteConfig getInstance(){
        if(INSTANCE == null){
            INSTANCE = new AppRemoteConfig();
        }
        return INSTANCE;
    }

    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    public static void setup(){
        getInstance().init();
    }

    private void init(){
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
    }

    public void update(Activity activity){
        mFirebaseRemoteConfig.fetch().addOnCompleteListener(activity, task -> {
            if(task.isSuccessful())
                mFirebaseRemoteConfig.activateFetched();
        });
    }

    public double getMinimumGrade(){
        return mFirebaseRemoteConfig.getDouble("minimum_grade");
    }



}

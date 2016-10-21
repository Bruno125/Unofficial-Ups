package com.brunoaybar.unofficialupc;

import android.app.Application;

import com.brunoaybar.unofficialupc.analytics.AnalyticsManager;

/**
 * Created by brunoaybar on 21/10/2016.
 */

public class UpcApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        AnalyticsManager.setup(this);
    }
}
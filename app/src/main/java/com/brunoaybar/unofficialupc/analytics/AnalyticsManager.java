package com.brunoaybar.unofficialupc.analytics;

import android.app.Application;

import com.amplitude.api.Amplitude;
import com.brunoaybar.unofficialupc.BuildConfig;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;


import io.fabric.sdk.android.Fabric;

/**
 * Class that will handle analytics libraries setup, events dispatching and other analytics related stuff
 */

public class AnalyticsManager {


    public static void setup(Application application){

        //Setup Fabric
        Fabric.with(application, new Crashlytics());

        //Setup application
        Amplitude.getInstance().initialize(application, BuildConfig.AMPLITUDE_SDK).enableForegroundTracking(application);

    }


}

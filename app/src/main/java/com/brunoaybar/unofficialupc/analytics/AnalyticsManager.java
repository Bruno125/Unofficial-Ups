package com.brunoaybar.unofficialupc.analytics;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.amplitude.api.Amplitude;
import com.brunoaybar.unofficialupc.BuildConfig;
import com.brunoaybar.unofficialupc.data.models.Course;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.SignUpEvent;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.internal.bind.util.ISO8601Utils;


import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Class that will handle analytics libraries setup, events dispatching and other analytics related stuff
 */

public class AnalyticsManager {

    public static void setup(Application application){

        //Setup Fabric
        Fabric.with(application, new Crashlytics());

        //Setup amplitude
        Amplitude.getInstance().initialize(application, BuildConfig.AMPLITUDE_SDK).enableForegroundTracking(application);

        //Setup Facebook
        FacebookSdk.sdkInitialize(application);
        AppEventsLogger.activateApp(application);
    }


    public static void eventLogin(Context context,boolean success){
        //Fabric
        Answers.getInstance().logSignUp(new SignUpEvent()
                .putMethod("Normal")
                .putSuccess(success));

        //Firebase
        Bundle params = new Bundle();
        params.putBoolean("Success",success);
        FirebaseAnalytics.getInstance(context).logEvent(AnalyticsConstants.EVENT_LOGIN,params);

        //Amplitude
        try {
            Amplitude.getInstance().logEvent(AnalyticsConstants.EVENT_LOGIN,new JSONObject().put("Success",success));
        } catch (JSONException e) {
            Crashlytics.logException(e);
        }

    }

    public static void eventCalculate(Context context,Course course, double grade){
        //Fabric
        Answers.getInstance().logCustom(new CustomEvent(AnalyticsConstants.EVENT_CALCULATION)
                .putCustomAttribute("Course name", course.getName())
                .putCustomAttribute("Course code", course.getCode())
                .putCustomAttribute("Result", grade));

        //Firebase
        Bundle params = new Bundle();
        params.putString("course_name",course.getName());
        params.putString("course_code",course.getCode());
        params.putDouble("result",grade);
        FirebaseAnalytics.getInstance(context).logEvent(AnalyticsConstants.EVENT_CALCULATION,params);

        //Amplitude
        try {
            Amplitude.getInstance().logEvent(AnalyticsConstants.EVENT_CALCULATION, new JSONObject()
                    .put("Course name",course.getName())
                    .put("Course code",course.getCode())
                    .put("Result",grade));
        } catch (JSONException e) {
            Crashlytics.logException(e);
        }

    }

    public static void eventLogout(Context context){
        //Fabric
        Answers.getInstance().logCustom(new CustomEvent(AnalyticsConstants.EVENT_LOGOUT));

        //Firebase
        FirebaseAnalytics.getInstance(context).logEvent(AnalyticsConstants.EVENT_LOGOUT,new Bundle());

        //Amplitude
        Amplitude.getInstance().logEvent(AnalyticsConstants.EVENT_LOGOUT);
    }

    //TODO: add Firebase
    public static void eventReserve(String resourceType, String venue, int hourOfDay){
        //Fabric
        Answers.getInstance().logCustom(new CustomEvent(AnalyticsConstants.EVENT_RESERVE)
                .putCustomAttribute("Resource Type", resourceType)
                .putCustomAttribute("Venue", venue)
                .putCustomAttribute("Hour", hourOfDay));
        //Amplitude
        try {
            Amplitude.getInstance().logEvent(AnalyticsConstants.EVENT_RESERVE, new JSONObject()
                    .put("Resource Type",resourceType)
                    .put("Venue",venue)
                    .put("Hour",hourOfDay));
        } catch (JSONException e) {
            Crashlytics.logException(e);
        }


    }

}

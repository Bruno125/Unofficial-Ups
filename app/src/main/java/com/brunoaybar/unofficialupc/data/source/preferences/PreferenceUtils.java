package com.brunoaybar.unofficialupc.data.source.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by brunoaybar on 14/10/2016.
 */

class PreferenceUtils {

    public static String getString(Context context, String key){
        return PreferencesFactory.getPreferences(context).getString(key,null);
    }

    public static void saveString(Context context, String key,String value){
        SharedPreferences.Editor editor = PreferencesFactory.getPreferences(context).edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static boolean getBool(Context context, String key){
        return getBool(context,key,false);
    }

    public static boolean getBool(Context context, String key,boolean defaultValue){
        return PreferencesFactory.getPreferences(context).getBoolean(key,defaultValue);
    }

    public static void saveBoolean(Context context, String key,boolean value){
        SharedPreferences.Editor editor = PreferencesFactory.getPreferences(context).edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

}

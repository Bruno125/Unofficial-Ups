package com.brunoaybar.unofficalupc.data.source.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by brunoaybar on 14/10/2016.
 */

public class PreferenceUtils {

    public static String getString(Context context, String key){
        return PreferencesFactory.getPreferences(context).getString(key,null);
    }

    public static void saveString(Context context, String key,String value){
        SharedPreferences.Editor editor = PreferencesFactory.getPreferences(context).edit();
        editor.putString(key,value);
        editor.apply();
    }

}

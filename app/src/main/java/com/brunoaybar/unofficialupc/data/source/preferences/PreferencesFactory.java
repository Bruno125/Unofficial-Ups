package com.brunoaybar.unofficialupc.data.source.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by brunoaybar on 14/10/2016.
 */

public class PreferencesFactory {

    private static final String PREFS_NAME = "unofficialUPC";

    public static SharedPreferences getPreferences(Context context){
        return context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
    }

}

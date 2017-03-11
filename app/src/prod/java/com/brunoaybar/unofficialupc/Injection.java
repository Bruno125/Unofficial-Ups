package com.brunoaybar.unofficialupc;

import android.content.Context;

import com.brunoaybar.unofficialupc.data.source.UpcRepository;
import com.brunoaybar.unofficialupc.data.source.preferences.UserPreferencesDataSource;
import com.brunoaybar.unofficialupc.data.source.remote.UpcServiceDataSource;

/**
 * Created by brunoaybar on 20/10/2016.
 */

public class Injection {

    public static UpcRepository provideUpcRepository(Context context){
        return new UpcRepository();
    }

    public static UpcServiceDataSource provideServiceSource(){
        return UpcServiceDataSource.getInstance();
    }

    public static UserPreferencesDataSource providePreferencesSource(Context context){
        return new UserPreferencesDataSource(context);
    }

}

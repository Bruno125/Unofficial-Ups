package com.brunoaybar.unofficialupc.utils;

import android.content.Context;

import com.brunoaybar.unofficialupc.utils.interfaces.StringProvider;

import javax.inject.Inject;

/**
 * Created by brunoaybar on 18/03/2017.
 */

public class AndroidStringProvider implements StringProvider {
    @Inject
    Context context;

    public AndroidStringProvider(Context context) {
        this.context = context;
    }

    @Override
    public String getString(int resId) {
        return context.getString(resId);
    }
}

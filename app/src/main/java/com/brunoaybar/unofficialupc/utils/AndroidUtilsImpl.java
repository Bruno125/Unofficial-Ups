package com.brunoaybar.unofficialupc.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import com.brunoaybar.unofficialupc.utils.interfaces.AndroidUtils;

/**
 * Created by fanlat on 29/03/17.
 */

public class AndroidUtilsImpl implements AndroidUtils{

    private Context context;

    public AndroidUtilsImpl(Context context) {
        this.context = context;
    }

    @Override
    public int getVersionCode() {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }
}

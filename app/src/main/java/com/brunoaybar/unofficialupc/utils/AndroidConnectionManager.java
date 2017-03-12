package com.brunoaybar.unofficialupc.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.utils.interfaces.InternetVerifier;

import javax.inject.Inject;

/**
 * Created by brunoaybar on 11/03/2017.
 */

public class AndroidConnectionManager implements InternetVerifier {

    @Inject Context context;

    public AndroidConnectionManager(Context context) {
        this.context = context;
    }

    @Override
    public Boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}

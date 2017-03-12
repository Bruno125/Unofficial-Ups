package com.brunoaybar.unofficialupc.data.models.errors;

import android.content.Context;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.UpcApplication;

import javax.inject.Inject;

/**
 * Created by brunoaybar on 11/03/2017.
 */

public class NoInternetException extends AppException {

    @Inject Context context;
    private String customMessage = "";

    public NoInternetException() {
        super(true, false);
        UpcApplication.getComponent().inject(this);
        customMessage = context.getString(R.string.error_no_internet);
    }

    @Override
    public String getMessage() {
        return customMessage;
    }
}

package com.brunoaybar.unofficialupc.utils;

import android.content.Context;

import com.brunoaybar.unofficialupc.utils.interfaces.DateProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateProviderImpl implements DateProvider{

    @Override
    public Date getNow() {
        return new Date();
    }

    @Override
    public Date getNever() {
        return new Date(0);
    }

    @Override
    public DateFormat getLocalFormatter(String pattern) {
        Locale locale = new Locale("es","PE");
        return new SimpleDateFormat(pattern,locale);
    }
}

package com.brunoaybar.unofficialupc.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by brunoaybar on 2/21/16.
 */
public enum Font {

    REGULAR("circular_book.otf"),
    LIGHT("circular_light.otf"),
    BOLD("circular_bold.otf");

    private String mFontName;

    Font(String pFileName) {
        mFontName = pFileName;
    }

    public static Font getFont(int position){
        return values()[position];
    }

    public Typeface getTypeface(Context context){
        return Typeface.DEFAULT;
        //return FontHelper.getTypeface(context,mFontName);
    }

}

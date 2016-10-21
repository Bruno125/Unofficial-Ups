package com.brunoaybar.unofficialupc.components.base;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.brunoaybar.unofficialupc.utils.FontHelper;
import com.brunoaybar.unofficialupc.utils.interfaces.Typefaceable;

/**
 * Created by brunoaybar on 14/10/2016.
 */

public class BaseTextView extends AppCompatTextView implements Typefaceable {

    private FontHelper mFontHelper;

    public BaseTextView(Context context) {
        super(context);
    }

    public BaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public BaseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    void init(Context context, AttributeSet attrs){
        if (!this.isInEditMode()){
            mFontHelper = new FontHelper();
            mFontHelper.init(context,attrs,this);
        }
    }

    @Override
    public void setTextAppearance(Context context, int resid) {
        Typeface typeface = getTypeface();
        super.setTextAppearance(context, resid);
        setTypeface(typeface);
    }

}

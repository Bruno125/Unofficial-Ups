package com.brunoaybar.unofficialupc.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.utils.interfaces.Typefaceable;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by brunoaybar on 2/16/16.
 */
public class FontHelper {
    private static Map<String, Typeface> mTypefaces;

    public void init(Context context, AttributeSet attrs, Typefaceable view) {
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomFont);
        if (array != null)
        {
            Typeface typeface = null;
            final int baseType = array.getInt(
                    R.styleable.CustomFont_baseTypeface,-1);
            final String customTypeFace = array.getString(
                    R.styleable.CustomFont_customTypeface);
            if(baseType!=-1)
                typeface = Font.getFont(baseType).getTypeface(context);
            else if(customTypeFace!=null)
                typeface = FontHelper.getTypeface(context,customTypeFace);
            else
                typeface = Font.REGULAR.getTypeface(context);

            if(typeface!=null)
                view.setTypeface(typeface);
            array.recycle();
        }
    }

    public static Typeface getTypeface(Context context, String typefaceAssetPath){
        Typeface typeface = null;
        if(!typefaceAssetPath.contains("fonts/"))
            typefaceAssetPath = String.format("fonts/%s",typefaceAssetPath);
        if(mTypefaces==null)
            mTypefaces = new HashMap<>();
        if (mTypefaces.containsKey(typefaceAssetPath)) {
            typeface = mTypefaces.get(typefaceAssetPath);
        } else
        {
            AssetManager assets = context.getAssets();
            typeface = Typeface.createFromAsset(assets, typefaceAssetPath);
            mTypefaces.put(typefaceAssetPath, typeface);
        }
        return typeface;
    }

}

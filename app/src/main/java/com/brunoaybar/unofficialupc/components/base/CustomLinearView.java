package com.brunoaybar.unofficialupc.components.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by brunoaybar on 19/10/2016.
 */

public class CustomLinearView extends LinearLayout {
    public CustomLinearView(Context context) {
        super(context);
    }

    @Override
    public void addView(View child) {
        throw new RuntimeException("Adding views not allowed");
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        throw new RuntimeException("Adding views not allowed");
    }

    @Override
    public void addView(View child, int width, int height) {
        throw new RuntimeException("Adding views not allowed");
    }

    @Override
    public void addView(View child, int index) {
        throw new RuntimeException("Adding views not allowed");
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        throw new RuntimeException("Adding views not allowed");
    }
}

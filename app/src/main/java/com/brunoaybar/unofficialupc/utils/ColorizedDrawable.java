package com.brunoaybar.unofficialupc.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

public final class ColorizedDrawable {
    private ColorizedDrawable() {
    }

    public static Drawable forIdStateList(Context context, int drawableId, int colorStateListId) {
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, drawableId));
        DrawableCompat.setTintList(drawable, ContextCompat.getColorStateList(context, colorStateListId));
        DrawableCompat.setTintMode(drawable, Mode.SRC_IN);
        return drawable;
    }

    public static Drawable forIdWithColors(Context context, int blackDrawableId, int baseId, int disabledId, int pressedId, int selectedId) {
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, blackDrawableId));
        int baseColor = ContextCompat.getColor(context, baseId);
        int disabledColor = ContextCompat.getColor(context, disabledId);
        int pressedColor = ContextCompat.getColor(context, pressedId);
        int selectedColor = ContextCompat.getColor(context, selectedId);
        int[][] states = new int[4][];
        states[0] = new int[]{-android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_pressed};
        states[2] = new int[]{android.R.attr.state_selected};
        states[3] = new int[]{android.R.attr.state_enabled};
        DrawableCompat.setTintList(drawable, new ColorStateList(states, new int[]{disabledColor, pressedColor, selectedColor, baseColor}));
        DrawableCompat.setTintMode(drawable, Mode.SRC_IN);
        return drawable;
    }

    public static Drawable forIdWithColor(Context context, int drawableId, int colorId) {
        return forDrawableWithColor(context, ContextCompat.getDrawable(context, drawableId), colorId);
    }

    public static Drawable forDrawableWithColor(Context context, Drawable drawable, int colorId) {
        return mutateDrawableWithColor(drawable, ContextCompat.getColor(context, colorId));
    }

    public static Drawable mutateDrawableWithColor(Drawable drawable, int color) {
        Drawable dst = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTint(dst, color);
        DrawableCompat.setTintMode(dst, Mode.SRC_IN);
        return dst;
    }

    public static Drawable forIdWithColorCatchingOOM(Context context, int drawableId, int colorId) {
        try {
            return forIdWithColor(context, drawableId, colorId);
        } catch (OutOfMemoryError e) {
            return null;
        }
    }
}
package com.brunoaybar.unofficialupc.modules.timetable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.utils.UiUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by brunoaybar on 16/10/2016.
 */

public class RoundCircleView extends View {

    private float currentSize;
    private Paint mPaint;

    public RoundCircleView(Context context, boolean isSelected) {
        super(context);
        init(context,isSelected, UiUtils.getPrimaryColor(context));

    }

    public RoundCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        boolean isSelected = false;
        int color = UiUtils.getPrimaryColor(context);
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WeekPickerAttrs);
        if(attrs!=null){
            isSelected = array.getBoolean(R.styleable.WeekPickerAttrs_isSelected,false);
            color = array.getColor(R.styleable.WeekPickerAttrs_pickerColor,UiUtils.getPrimaryColor(context));
            array.recycle();
        }
        init(context,isSelected,color);
    }

    private void init(Context context, boolean isSelected ,@ColorInt int color){
        //Set circle size
        currentSize = 25;
        //Set circle color
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if(!isInEditMode()) {
            mPaint.setColor(isSelected ? color : ContextCompat.getColor(context, R.color.week_picker_inactive));
        }
    }

    public void setState(boolean selected){
        setColor( ContextCompat.getColor(getContext(), selected ? R.color.primary : R.color.week_picker_inactive ));
    }

    public void setColor(@ColorInt Integer destinationColor){
        if(isInEditMode()){
            mPaint.setColor(destinationColor);
            this.invalidate();
            return;
        }

        int currentColor = mPaint.getColor();

        Integer[] intermediateColors = new Integer[10];
        for(int i=1;i<=10;i++)
            intermediateColors[i-1] = UiUtils.blendColors(destinationColor,currentColor,0.1f*i);

        // Timer observable that will emit every half second.
        Observable<Long> timerObservable = Observable.interval(0, 20, TimeUnit.MILLISECONDS);
        Observable<Integer> colorsObservable = Observable.from(intermediateColors);
        // First, zip the timer and circle views observables, so that we get one circle view every half a second.
        Observable.zip(colorsObservable, timerObservable, (color, aLong) -> color)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(color -> {
                    mPaint.setColor(color);
                    this.invalidate();
                });


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int totalWidth = getWidth();
        int totalHeight = getHeight();

        canvas.drawCircle(totalWidth/2,totalHeight/2,currentSize,mPaint);
    }
}

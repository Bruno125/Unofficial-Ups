package com.brunoaybar.unofficalupc.modules.timetable;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.brunoaybar.unofficalupc.R;
import com.brunoaybar.unofficalupc.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Component that will display the seven days of the week and
 * will notify whenever the user selects a new event
 */

public class WeekDatePicker extends RelativeLayout{

    private LinearLayout mBulletsLayout;
    private View mLine;
    private int mNumberOfDays = 7;
    private List<WeekDayItemView> mWeekDayItems;

    public WeekDatePicker(Context context) {
        super(context);
        initWithSelected(context,0);
    }

    public WeekDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        int selected = 0;
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.WeekPickerAttrs);
        if(array != null) {
            selected = array.getInteger(R.styleable.WeekPickerAttrs_isSelected, 0);
            array.recycle();
        }
        initWithSelected(context,selected);

    }

    private void initWithSelected(Context context, int selected){

        //Add line behind
        if(mNumberOfDays > 0){
            LayoutParams lineParams =new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,4);
            mLine= new View(context);
            mLine.setLayoutParams(lineParams);
            mLine.setBackgroundColor(ContextCompat.getColor(context,R.color.week_picker_inactive));
            addView(mLine,lineParams);

        }

        //Create container layout
        mBulletsLayout = new LinearLayout(context);
        mBulletsLayout.setOrientation(LinearLayout.HORIZONTAL);
        //Setup size
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,getContext().getResources().getDimensionPixelSize(R.dimen.week_picker_height));
        mBulletsLayout.setLayoutParams(params);
        //Add layout to root view
        addView(mBulletsLayout);

        //Create items for each day
        Calendar c = GregorianCalendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        setDatesStartingIn(context,c.getTime(),new Date());

    }

    public void setNumberOfDays(int numberOfDays){
        if(numberOfDays < 0)
            throw new IllegalArgumentException();
        mNumberOfDays = numberOfDays;
    }

    public void setDatesStartingIn(Context context,Date startDate, Date selectedDate){
        mBulletsLayout.removeAllViews();
        mWeekDayItems = new ArrayList<>();

        Calendar c = GregorianCalendar.getInstance();
        c.setTime(startDate);
        for(int i=0; i<mNumberOfDays ;i++){
            Date currentDate = c.getTime();
            boolean isSelected = Utils.sameDay(currentDate,selectedDate);
            //Create day item
            WeekDayItemView item = new WeekDayItemView(context,currentDate,isSelected);
            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
            item.setLayoutParams(itemParams);

            //Add item to the layout
            mBulletsLayout.addView(item);
            mWeekDayItems.add(item);

            //Increase date
            c.add(Calendar.DATE,1);
        }

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else{
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                updateLineMargins(mWeekDayItems.get(0));
            }
        });
    }

    private void updateLineMargins(WeekDayItemView item){
        int positionX = item.getWidth() / 2;
        LayoutParams params = (LayoutParams) mLine.getLayoutParams();
        params.setMargins(positionX,item.getCircleCenter(),positionX,0);
        mLine.setLayoutParams(params);
    }

}

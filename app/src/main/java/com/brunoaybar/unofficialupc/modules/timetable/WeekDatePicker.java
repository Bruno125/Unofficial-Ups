package com.brunoaybar.unofficialupc.modules.timetable;

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

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Component that will display the seven days of the week and
 * will notify whenever the user selects a new event
 */

public class WeekDatePicker extends RelativeLayout{

    public interface OnDateSelectionListener{
        void onDateSelected(Date date);
    }

    private OnDateSelectionListener mListener;
    public void setListener(OnDateSelectionListener listener) {
        this.mListener = listener;
    }

    private LinearLayout mBulletsLayout;
    private View mLine;
    private int mNumberOfDays = 7;
    private List<WeekDayItemView> mWeekDayItems;
    private int mSelectedIndex = -1;

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
        Calendar c = Calendar.getInstance();
        // Set the calendar to monday of the current week
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
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
            if(isSelected)
                mSelectedIndex = i;
            //Create day item
            WeekDayItemView item = new WeekDayItemView(context,currentDate,isSelected);
            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
            item.setLayoutParams(itemParams);

            //Add item to the layout
            mBulletsLayout.addView(item);
            mWeekDayItems.add(item);

            //Set listener
            final int index = i;
            item.setClickable(true);
            item.setOnClickListener(v -> updateSelection(index));

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

    private void updateSelection(int selected){
        //If selected the same, do nothing
        if(mSelectedIndex == selected)
            return;
        //Set previous selected item to inactive state
        if(mSelectedIndex!=-1)
            mWeekDayItems.get(mSelectedIndex).setSelected(false);
        //Update selected
        mSelectedIndex = selected;
        mWeekDayItems.get(selected).setSelected(true);

        if(mListener!=null)
            mListener.onDateSelected(mWeekDayItems.get(selected).getDate());
    }

    public Date getSelectedDate(){
        try{
            return mWeekDayItems.get(mSelectedIndex).getDate();
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public void setSelectedDate(Date date){
        if(mWeekDayItems==null)
            return;
        int index = mSelectedIndex,c = 0;
        for (WeekDayItemView item : mWeekDayItems){
            if(Utils.sameDay(item.getDate(),date)){
                index = c;
                break;
            }
            c++;
        }
        updateSelection(index);
    }

}

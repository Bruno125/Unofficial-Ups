package com.brunoaybar.unofficialupc.modules.timetable;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brunoaybar.unofficialupc.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by brunoaybar on 16/10/2016.
 */

public class WeekDayItemView extends LinearLayout {


    private boolean isSelected;
    private Date mDate;
    private TextView tviDay;
    private TextView tviDateNumber;
    private RoundCircleView roundCircleView;

    public WeekDayItemView(Context context, Date date,boolean isSelected) {
        super(context);
        init(context,date,isSelected);
    }

    private void init(Context context,Date date,boolean selected){
        LayoutInflater  mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.view_week_date_item, this, true);


        tviDateNumber = (TextView) findViewById(R.id.tviDateNumber);
        tviDay = (TextView) findViewById(R.id.tviDay);
        roundCircleView = (RoundCircleView) findViewById(R.id.roundCircleView);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        tviDateNumber.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));

        SimpleDateFormat sdf = new SimpleDateFormat("E",new Locale("es"));
        tviDay.setText(sdf.format(date));

        mDate = date;
        setSelected(selected);
    }

    public int getCircleCenter(){
        return (int) (roundCircleView.getY() + roundCircleView.getHeight() / 2);
    }

    public void setSelected(boolean selected){
        if(isSelected!=selected){
            isSelected = selected;
            ViewCompat.animate(roundCircleView)
                    .scaleX(isSelected ? 2.0f : 1.0f)
                    .scaleY(isSelected ? 2.0f : 1.0f)
                    .setDuration(200);
            roundCircleView.setState(isSelected);

            ViewCompat.animate(tviDateNumber)
                    .scaleX(isSelected ? 1.0f : 0.0f)
                    .scaleY(isSelected ? 1.0f : 0.0f)
                    .setDuration(200);
        }
    }

    public Date getDate(){
        return mDate;
    }

}

package com.brunoaybar.unofficalupc.modules.timetable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brunoaybar.unofficalupc.R;
import com.brunoaybar.unofficalupc.data.models.Timetable;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunoaybar on 17/10/2016.
 */

public class DayScheduleView extends LinearLayout {

    public DayScheduleView(Context context) {
        super(context);
        init(context);
    }

    public DayScheduleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @BindView(R.id.llaHours) LinearLayout llaHours;
    @BindView(R.id.rlaEvents) RelativeLayout rlaEvents;
    @BindDimen(R.dimen.schedule_hour_height) int rowHeight;

    private void init(Context context){
        LayoutInflater  mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.view_day_schedule, this, true);
        ButterKnife.bind(this,this);

        int nHoursPerDay = 24;
        //Populate hours
        for(int i=0; i<nHoursPerDay;i++)
            llaHours.addView(new HourView(context,i,rowHeight));

        ViewGroup.LayoutParams params = rlaEvents.getLayoutParams();
        params.height = rowHeight * nHoursPerDay;
        rlaEvents.setLayoutParams(params);

    }

    public void addEvent(Timetable.Class event){

    }


    class HourView extends LinearLayout{


        @BindView(R.id.containerView) View containerView;
        @BindView(R.id.tviHour) TextView tviHour;

        HourView(Context context, int position, int rowSize){
            super(context);
            init(context,position,rowSize);
        }

        private void init(Context context,int position,int rowSize){
            LayoutInflater  mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mInflater.inflate(R.layout.item_schedule_hour, this, true);
            ButterKnife.bind(this,this);

            tviHour.setText(getHour(position));

            ViewGroup.LayoutParams params = containerView.getLayoutParams();
            params.height = rowSize;
            containerView.setLayoutParams(params);
        }


        private String getHour(int position){
            String suffix = " AM";
            if(position>12){
                position-=12;
                suffix = " PM";
            }
            return position + suffix;
        }

    }

}

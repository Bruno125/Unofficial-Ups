package com.brunoaybar.unofficialupc.modules.timetable;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.data.models.Timetable;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

    private int startingHour = 7;
    private int nHoursPerDay = 24;

    private void init(Context context){
        LayoutInflater  mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.view_day_schedule, this, true);
        ButterKnife.bind(this,this);
        //Populate hours
        for(int i=startingHour; i<nHoursPerDay;i++)
            llaHours.addView(new HourView(context,i,rowHeight));

        ViewGroup.LayoutParams params = rlaEvents.getLayoutParams();
        params.height = rowHeight * (nHoursPerDay - startingHour);
        rlaEvents.setLayoutParams(params);
    }

    public void setDay(Timetable.Day day){
        rlaEvents.removeAllViews();
        if(day!=null) {
            for (Timetable.Class mClass : day.getClasses())
                addEvent(mClass);
        }
    }

    public void addEvent(Timetable.Class event){
        EventView eventView = new EventView(getContext(),event);

        GregorianCalendar time = new GregorianCalendar();
        time.setTime(event.getDate());
        int classStartHour = time.get(Calendar.HOUR_OF_DAY);

        int marginTop = (classStartHour - startingHour) * rowHeight;
        int height = rowHeight*event.getDuration();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
        params.setMargins(rowHeight,marginTop,0,0);
        rlaEvents.addView(eventView,params);

        eventView.setClickable(true);
        if(mListener!=null)
            eventView.setOnClickListener(v -> mListener.onSelectedClass(event));

    }


    interface OnClassSelectedListener{
        void onSelectedClass(Timetable.Class mClass);
    }

    private OnClassSelectedListener mListener;
    public void setListener(OnClassSelectedListener listener) {
        mListener = listener;
    }


    class EventView extends LinearLayout{

        @BindView(R.id.tviCodeAndSection) TextView tviCodeAndSection;
        @BindView(R.id.tviName) TextView tviName;
        @BindView(R.id.tviPlace) TextView tviPlace;

        private View cardView;

        public EventView(Context context,Timetable.Class event) {
            super(context);
            init(context,event);
        }

        private void init(Context context,Timetable.Class event){
            LayoutInflater  mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cardView = mInflater.inflate(R.layout.view_schedule_event, this, false);
            ButterKnife.bind(this,cardView);
            setBackgroundColor(Color.TRANSPARENT);

            tviCodeAndSection.setText(event.getCourseCode() + " - " + event.getSection());
            tviName.setText(event.getCourseName());
            tviPlace.setText(event.getVenue() + " - " + event.getRoom());

            if(event.getDuration() == 2){
                tviName.setMaxLines(1);
                tviName.setEllipsize(TextUtils.TruncateAt.END);
            }

            addView(cardView);
        }

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

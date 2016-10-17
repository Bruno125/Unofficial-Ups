package com.brunoaybar.unofficalupc.modules.timetable;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brunoaybar.unofficalupc.R;

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

    @BindView(R.id.rviHours) RecyclerView rviHours;
    @BindDimen(R.dimen.schedule_hour_height) int rowHeight;

    private void init(Context context){
        LayoutInflater  mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.view_day_schedule, this, true);
        ButterKnife.bind(this,this);

        rviHours.setLayoutManager(new LinearLayoutManager(context));
        rviHours.setAdapter(new HoursAdapter(context,rowHeight));


    }


    class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.ViewHolder>{

        private int mRowSize;
        private Context mContext;

        HoursAdapter(Context context,int rowSize){
            mRowSize = rowSize;
            mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_schedule_hour,parent,false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tviHour.setText(getHour(position));

            ViewGroup.LayoutParams params = holder.containerView.getLayoutParams();
            params.height = mRowSize;
            holder.containerView.setLayoutParams(params);
        }

        @Override
        public int getItemCount() {
            return 24;
        }

        private String getHour(int position){
            String suffix = " AM";
            if(position>12){
                position-=12;
                suffix = " PM";
            }
            return position + suffix;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.containerView) RelativeLayout containerView;
            @BindView(R.id.tviHour) TextView tviHour;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }

}

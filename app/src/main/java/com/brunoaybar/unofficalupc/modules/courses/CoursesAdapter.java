package com.brunoaybar.unofficalupc.modules.courses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brunoaybar.unofficalupc.R;
import com.brunoaybar.unofficalupc.data.models.Course;
import com.brunoaybar.unofficalupc.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunoaybar on 16/10/2016.
 */

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tviCode) TextView tviCode;
        @BindView(R.id.tviName) TextView tviName;
        @BindView(R.id.tviHintProgress) TextView tviHintProgress;
        @BindView(R.id.viewColorIndicator) View viewColorIndicator;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    private List<Course> mCourses;
    private Context mContext;

    public CoursesAdapter(Context context){
        this(context,new ArrayList<>());
    }

    public CoursesAdapter(@NonNull Context context, @NonNull List<Course> courses) {
        mContext = context;
        mCourses = courses;
    }

    public void addCourse(Course course){
        mCourses.add(course);
        notifyItemInserted(mCourses.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_course,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = mCourses.get(position);
        holder.tviCode.setText(course.getCode());
        holder.tviName.setText(course.getName());

        int color = getColorForPosition(position);
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        holder.viewColorIndicator.setBackgroundColor(color);

        String hint = mContext.getString(R.string.text_hint_course_progress);
        hint = hint.replace("#EE0000","#888888");
        UiUtils.setHtmlText(holder.tviHintProgress,hint);
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public int getColorForPosition(int position){
        position = position % 5;
        switch (position){
            case 0:
                return ContextCompat.getColor(mContext,R.color.material_orange);
            case 1:
                return ContextCompat.getColor(mContext,R.color.material_amber);
            case 2:
                return ContextCompat.getColor(mContext,R.color.material_green);
            case 3:
                return ContextCompat.getColor(mContext,R.color.material_blue);
            default:
                return ContextCompat.getColor(mContext,R.color.material_purple);
        }
    }



}

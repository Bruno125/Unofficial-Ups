package com.brunoaybar.unofficialupc.modules.classmates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.data.models.Classmate;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter class that will receive the menu_course data, and handle it's display
 * within a RecyclerView
 */

public class ClassmatesAdapter extends RecyclerView.Adapter<ClassmatesAdapter.ViewHolder>{

    @NonNull
    private List<Classmate> mClassmates;
    @NonNull
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tviName) TextView tviName;
        @BindView(R.id.tviCode) TextView tviCode;
        @BindView(R.id.tviCareer) TextView tviCareer;
        @BindView(R.id.iviPhoto) ImageView iviPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public ClassmatesAdapter(@NonNull Context context){
        this(context, new ArrayList<>());
    }

    public ClassmatesAdapter(@NonNull Context context,@NonNull List<Classmate> classmates){
        mClassmates = classmates;
        mContext = context;
    }

    @Override
    public ClassmatesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_classmate,parent,false));
    }

    @Override
    public void onBindViewHolder(ClassmatesAdapter.ViewHolder holder, int position) {
        Classmate classmate = mClassmates.get(position);
        holder.tviName.setText(classmate.getName());
        holder.tviCode.setText(classmate.getCode());
        holder.tviCareer.setText(classmate.getCareer());
        Glide.with(mContext).load(classmate.getPhoto()).into(holder.iviPhoto);
    }

    @Override
    public int getItemCount() {
        return mClassmates.size();
    }

    public void addClassmate(Classmate classmate){
        mClassmates.add(classmate);
        notifyItemChanged(mClassmates.size()-1);
    }

}

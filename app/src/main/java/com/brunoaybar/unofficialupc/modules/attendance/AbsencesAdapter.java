package com.brunoaybar.unofficialupc.modules.attendance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.data.models.Absence;
import com.brunoaybar.unofficialupc.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunoaybar on 18/10/2016.
 */

public class AbsencesAdapter extends RecyclerView.Adapter<AbsencesAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tviName)TextView tviName;
        @BindView(R.id.tviTotal)TextView tviTotal;
        @BindView(R.id.tviMaximum)TextView tviMaximum;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    private Context mContext;
    @NonNull
    private List<Absence> mAbsences;

    public AbsencesAdapter(@NonNull Context context){
        this(context, new ArrayList<>());
    }

    public AbsencesAdapter(@NonNull Context context, @NonNull List<Absence> absences){
        mContext = context;
        mAbsences = absences;
    }

    public void addAbsence(Absence absence){
        mAbsences.add(absence);
        notifyItemInserted(mAbsences.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_absence,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int type = getItemViewType(position);

        if(type == TYPE_HEADER){
            int color = ContextCompat.getColor(mContext,R.color.primary_dark);
            UiUtils.setTextColor(color,holder.tviName,holder.tviTotal,holder.tviMaximum);
        }else{
            Absence absence = mAbsences.get(position-1);
            holder.tviName.setText(absence.getCourseName());
            holder.tviTotal.setText(String.valueOf(absence.getTotal()));
            holder.tviMaximum.setText(String.valueOf(absence.getMaximum()));
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mAbsences.size() + 1;
    }

    public List<Absence> getItems(){
        return mAbsences;
    }

}

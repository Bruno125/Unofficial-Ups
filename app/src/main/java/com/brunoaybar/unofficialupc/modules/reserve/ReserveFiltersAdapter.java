package com.brunoaybar.unofficialupc.modules.reserve;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.data.models.ReserveFilter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunoaybar on 18/03/2017.
 */

public class ReserveFiltersAdapter extends RecyclerView.Adapter<ReserveFiltersAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tviFilterName) TextView tviFilterName;
        @BindView(R.id.tviFilterValues) TextView tviFilterValues;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private List<DisplayableReserveFilter> items;
    private Context context;

    public ReserveFiltersAdapter(Context context){
        this.context = context;
    }

    public void update(List<DisplayableReserveFilter> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_reserve_filter,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DisplayableReserveFilter filter = items.get(position);
        holder.tviFilterName.setText(filter.getName());

        String temp = "";
        for(ReserveFilter.ReserveOption option : filter.getValues()){
            temp = temp + option.getValue() + ", ";
        }
        holder.tviFilterValues.setText(temp);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    interface Callback {
        void updated(List<DisplayableReserveFilter> entries, int position, int selectedValue);
    }

    private Callback listener;
    public void setListener(Callback listener){
        this.listener = listener;
    }

}

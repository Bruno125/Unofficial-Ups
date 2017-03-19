package com.brunoaybar.unofficialupc.modules.reserve;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.data.models.ReserveFilter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunoaybar on 18/03/2017.
 */

public class ReserveFiltersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tviFilterName) TextView tviFilterName;
        @BindView(R.id.tviFilterHint) TextView tviFilterHint;
        @BindView(R.id.iviStatus) ImageView iviStatus;
        View containerView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            containerView = itemView;
            ButterKnife.bind(this,itemView);
        }
    }
    public class ValueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tviFilterValue) TextView tviFilterName;
        @BindView(R.id.iviSelected) ImageView iviSelected;
        View containerView;

        public ValueViewHolder(View itemView) {
            super(itemView);
            containerView = itemView;
            ButterKnife.bind(this,itemView);
        }
    }

    private List<DisplayableReserveFilter> items;
    private List<InfoCell> itemsInfo;
    private Context context;

    public ReserveFiltersAdapter(Context context){
        this.context = context;
    }

    public void update(List<DisplayableReserveFilter> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ViewHolderType.HEADER.value){
            return new HeaderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_reserve_filter,parent,false));
        }else{
            return new ValueViewHolder(LayoutInflater.from(context).inflate(R.layout.item_reserve_filter_value,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        InfoCell info = itemsInfo.get(position);
        DisplayableReserveFilter filter = items.get(info.position);
        switch (info.type){
            case HEADER:
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                headerViewHolder.tviFilterName.setText(filter.getName());
                headerViewHolder.tviFilterHint.setText(filter.getHint());
                headerViewHolder.containerView.setOnClickListener(view -> listener.touchedFilterHeader(items,info.position));
                if(filter.isSelected()){
                    headerViewHolder.iviStatus.setImageResource(R.drawable.ic_check_circle_primary_24dp);
                }else{
                    headerViewHolder.iviStatus.setImageResource(R.drawable.ic_check_circle_gray_24dp);
                }
                break;
            case VALUE:
                ValueViewHolder valueViewHolder = (ValueViewHolder) holder;
                ReserveFilter.ReserveOption option = filter.getValues().get(info.valuePosition);
                valueViewHolder.tviFilterName.setText(option.getValue());
                boolean isSelected = filter.getSelectedValue() == info.valuePosition;
                valueViewHolder.iviSelected.setVisibility(isSelected ? View.VISIBLE : View.INVISIBLE);
                valueViewHolder.containerView.setOnClickListener(view -> listener.selectedFilterValue(items,info.position,info.valuePosition));
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }

        itemsInfo = new ArrayList<>();
        for (int i=0;i<items.size();i++){
            DisplayableReserveFilter filter=items.get(i);
            itemsInfo.add(new InfoCell(i));

            if(filter.getExpanded()){
                for(int k = 0; k < filter.getValuesCount(); k++){
                    itemsInfo.add(new InfoCell(i,k));
                }
            }
        }
        return itemsInfo.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itemsInfo.get(position).getType();
    }

    interface Callback {
        void touchedFilterHeader(List<DisplayableReserveFilter> entries, int position);
        void selectedFilterValue(List<DisplayableReserveFilter> entries, int position, int selectedValue);
    }

    private Callback listener;
    public void setListener(Callback listener){
        this.listener = listener;
    }

    enum ViewHolderType{
        HEADER(0),VALUE(1);
        private int value;
        ViewHolderType(int value) {
            this.value = value;
        }
    }

    private class InfoCell{
        private ViewHolderType type;
        private int position;
        private int valuePosition;

        public InfoCell(int position) {
            this.type = ViewHolderType.HEADER;
            this.position = position;
        }

        public InfoCell(int position, int valuePosition) {
            this.type = ViewHolderType.VALUE;
            this.position = position;
            this.valuePosition = valuePosition;
        }

        public int getType(){
            return type.value;
        }

    }

}

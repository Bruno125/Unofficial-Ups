package com.brunoaybar.unofficalupc.modules.courses.calculate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.brunoaybar.unofficalupc.R;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunoaybar on 19/10/2016.
 */

public class CalculationAdapter extends RecyclerView.Adapter<CalculationAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tviType) TextView tviName;
        @BindView(R.id.tviWeight) TextView tviWeight;
        @BindView(R.id.eteGrade) EditText eteGrade;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    private List<DisplayableCalculation> mCalculations;
    @NonNull
    private Context mContext;
    private Callback mListener;

    public CalculationAdapter(@NonNull Context context){
        this(context,new ArrayList<>());
    }

    public CalculationAdapter(@NonNull  Context context,@NonNull List<DisplayableCalculation> calculations){
        mContext = context;
        mCalculations = calculations;
    }

    public List<DisplayableCalculation> getItems(){
        return mCalculations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_calculate,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DisplayableCalculation calculation = mCalculations.get(position);

        holder.tviName.setText(calculation.getName());
        holder.tviWeight.setText(calculation.getWeight());
        holder.eteGrade.setText(calculation.getGrade());
        holder.eteGrade.setEnabled(calculation.isEditingEnabled());

        int editTextColor = calculation.hasError() ? R.color.red : R.color.secondary_text;
        holder.eteGrade.setTextColor(ContextCompat.getColor(mContext,editTextColor));

        if(mListener!=null){
            RxTextView.afterTextChangeEvents(holder.eteGrade)
                    .map(input -> input.editable().toString())
                    .debounce(1, TimeUnit.SECONDS)
                    .subscribe(text -> {
                        calculation.setGrade(text);
                        //mListener.onCalculationModified(calculation,position);
                    });
        }
    }

    @Override
    public int getItemCount() {
        return mCalculations.size();
    }

    public void addCalculation(DisplayableCalculation calculation){
        mCalculations.add(calculation);
        notifyItemInserted(mCalculations.size()-1);
    }

    public void updateItem(DisplayableCalculation calculation, int position){
        if(position<0 || position>=mCalculations.size())
            return;
        mCalculations.set(position,calculation);
        notifyItemChanged(position);
    }


    interface Callback{
        void onCalculationModified(DisplayableCalculation calculation, int position);
    }

    public void setListener(Callback listener){
        mListener = listener;
    }

}

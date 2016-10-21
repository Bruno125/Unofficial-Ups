package com.brunoaybar.unofficialupc.modules.courses.calculate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.brunoaybar.unofficialupc.R;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunoaybar on 19/10/2016.
 */

public class CalculationsView extends ScrollView {

    public class Item extends LinearLayout{

        @BindView(R.id.tviType) TextView tviName;
        @BindView(R.id.tviWeight) TextView tviWeight;
        @BindView(R.id.eteGrade) EditText eteGrade;
        @BindView(R.id.container) View container;

        Item(Context context) {
            super(context);
            LayoutInflater.from(context).inflate(R.layout.item_calculate,this,true);
            ButterKnife.bind(this);
        }
    }

    @NonNull
    private List<DisplayableCalculation> mCalculations;
    @NonNull
    private Context mContext;
    @NonNull
    private List<Item> mItems;
    private LinearLayout container;
    private Callback mListener;

    public CalculationsView(@NonNull Context context){
        super(context);
        init(context);
    }

    public CalculationsView(@NonNull Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        mCalculations = new ArrayList<>();
        mItems = new ArrayList<>();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500);
        container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        addView(container,params);
    }

    public List<DisplayableCalculation> getItems(){
        return mCalculations;
    }

    private void bind(int position) {
        Item holder = mItems.get(position);
        DisplayableCalculation calculation = mCalculations.get(position);

        holder.tviName.setText(calculation.getName());
        holder.tviWeight.setText(calculation.getWeight());
        holder.eteGrade.setText(calculation.getGrade());
        holder.eteGrade.setEnabled(calculation.isEditingEnabled());

        int editTextColor = calculation.hasError() ? R.color.red : R.color.secondary_text;
        holder.eteGrade.setTextColor(ContextCompat.getColor(mContext,editTextColor));
        holder.eteGrade.setSelection(holder.eteGrade.getText().length());

        if(mListener!=null){
            RxTextView.afterTextChangeEvents(holder.eteGrade)
                    .map(input -> input.editable().toString())
                    .debounce(100, TimeUnit.MILLISECONDS)
                    .subscribe(text -> {
                        boolean hasChanged = !text.equals(calculation.getGrade());
                        calculation.setGrade(text);
                        if(hasChanged)
                            mListener.onCalculationModified(calculation,position);
                    });
        }
    }


    public int getItemCount() {
        return mCalculations.size();
    }

    public void addCalculation(DisplayableCalculation calculation){
        //Add calculation to local list
        mCalculations.add(calculation);
        //Create new row and  add it to the container
        Item item = new Item(mContext);
        container.addView(item);
        //Add item to current local item list
        mItems.add(item);
        //Bind data for that new item
        bind(mCalculations.size()-1);

    }

    public void updateItem(DisplayableCalculation calculation, int position){
        if(position<0 || position>=mCalculations.size())
            return;
        mCalculations.set(position,calculation);
        bind(position);
    }

    @Override
    public void removeAllViews() {
        container.removeAllViews();
        mItems = new ArrayList<>();
        mCalculations = new ArrayList<>();
    }

    interface Callback{
        void onCalculationModified(DisplayableCalculation calculation, int position);
    }

    public void setListener(Callback listener){
        mListener = listener;
    }

}

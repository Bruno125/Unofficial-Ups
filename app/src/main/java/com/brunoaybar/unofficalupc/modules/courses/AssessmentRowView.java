package com.brunoaybar.unofficalupc.modules.courses;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brunoaybar.unofficalupc.R;
import com.brunoaybar.unofficalupc.data.models.Assessment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunoaybar on 17/10/2016.
 */

public class AssessmentRowView extends LinearLayout{

    @BindView(R.id.tviType) TextView tviType;
    @BindView(R.id.tviWeight) TextView tviWeight;
    @BindView(R.id.tviGrade) TextView tviGrade;

    public AssessmentRowView(Context context, Assessment assessment) {
        super(context);
        init(context,assessment,false,false);
    }

    public AssessmentRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        boolean isHeader = false, isFooter = false;
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.AssessmentRowAttrs);
        if(array!=null){
            isHeader = array.getBoolean(R.styleable.AssessmentRowAttrs_isHeader,false);
            isFooter = array.getBoolean(R.styleable.AssessmentRowAttrs_isFooter,false);
            array.recycle();
        }
        init(context,null,isHeader,isFooter);
    }

    private void init(Context context, Assessment assessment, boolean isHeader, boolean isFooter){
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_row_assesment,this,true);
        ButterKnife.bind(this);
        setBackgroundColor(ContextCompat.getColor(context,R.color.white));
        if(isHeader){
            int color = ContextCompat.getColor(context,R.color.primary_dark);
            setTextColor(color,tviType,tviWeight,tviGrade);
            tviType.setText(context.getString(R.string.text_type));
            tviWeight.setText(context.getString(R.string.text_weight));
            tviGrade.setText(context.getString(R.string.text_grade));
        }else if(isFooter){
            setBackgroundColor(ContextCompat.getColor(context,R.color.divider));
        }else if(assessment!=null){
            setAssessment(context,assessment);
        }

    }

    public void setAssessment(Context context, Assessment assessment){
        tviType.setText(assessment.getName());
        String weight = String.format(context.getString(R.string.format_weight),assessment.getWeight());
        tviWeight.setText(weight);
        tviGrade.setText(assessment.getGrade());
    }

    private void setTextColor(@ColorInt int color,TextView... textViews){
        for (TextView textView : textViews){
            if(textView!=null)
                textView.setTextColor(color);
        }
    }

}

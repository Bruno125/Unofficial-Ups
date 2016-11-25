package com.brunoaybar.unofficialupc.modules.courses.calculate;

import android.content.Context;
import android.text.TextUtils;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.analytics.AppRemoteConfig;
import com.brunoaybar.unofficialupc.data.models.Course;

import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * View Model for the CalculateActivity
 */

public class CalculateViewModel {

    private Context mContext;
    public CalculateViewModel(Context context){
        mContext = context;
    }

    public Observable<DisplayableCalculation> getCalculationsStream(Course course){
        return Observable.from(course.getAssesments())
                .map(assessment -> {
                    DisplayableCalculation calculation = new DisplayableCalculation();
                    calculation.setName(assessment.getName());
                    calculation.setWeight(assessment.getWeight());

                    String grade = assessment.getGrade();
                    if( assessment.getGrade().equals("0"))
                        grade = "";
                    else if(assessment.getGrade().equals("NR"))
                        grade = "0";
                    calculation.setGrade(grade);

                    calculation.setEditingEnabled(true);
                    return calculation;
                });
    }


    class DisplayCalculationUpdate{
        private DisplayableCalculation calculation;
        private int position;

        public DisplayCalculationUpdate(DisplayableCalculation calculation,int position){
            this.calculation = calculation;
            this.position = position;
        }

        public DisplayableCalculation getCalculation() {
            return calculation;
        }

        public int getPosition() {
            return position;
        }
    }

    private BehaviorSubject<DisplayCalculationUpdate> mCalculationUpdateSubject = BehaviorSubject.create();
    public Observable<DisplayCalculationUpdate> getCalculationUpdateStream(){
        return mCalculationUpdateSubject.asObservable();
    }

    public void updatedCalculation(DisplayableCalculation calculation,int position){
        try{
            double grade = Double.parseDouble(calculation.getGrade());
            calculation.setHasError(!isValidGrade(grade));
            mCalculationUpdateSubject.onNext(new DisplayCalculationUpdate(calculation,position));
        }catch (NumberFormatException e){
            calculation.setHasError(true);
            mCalculationUpdateSubject.onNext(new DisplayCalculationUpdate(calculation,position));
        }

    }

    private boolean isValidGrade(double grade){
        return grade >=0 && grade<=20;
    }

    class CalculationResult{
        private boolean failed;
        private String title;
        private String message;
        private boolean approved;
        private double grade;

        public CalculationResult(String title, String message, double grade, boolean approved){
            this(title,message,grade,approved,false);
        }

        public CalculationResult(String message){
            this(null,message,0,false,true);
        }

        private CalculationResult(String title, String message,double grade, boolean approved,boolean didFail){
            this.title = title;
            this.message = message;
            this.approved = approved;
            this.failed = didFail;
            this.grade = grade;
        }

        public String getTitle() {
            return title;
        }

        public String getMessage() {
            return message;
        }

        public boolean isApproved() {
            return approved;
        }

        public boolean didFail() {
            return failed;
        }

        public double getGrade() {
            return grade;
        }

    }


    private BehaviorSubject<CalculationResult> mCalculationResultSubject = BehaviorSubject.create();
    public Observable<CalculationResult> getCalculationResultsStream(){
        return mCalculationResultSubject.asObservable();
    }

    public void requestedCalculate(List<DisplayableCalculation> calculations){
        if(calculations==null || calculations.size()<1)
            return;


        double total = 0;
        //Evaluate each row
        for(DisplayableCalculation calculation : calculations){
            try{
                //Check if field is not empty
                boolean isEmpty = TextUtils.isEmpty(calculation.getGrade());
                if(isEmpty){
                    mCalculationResultSubject.onNext(getError(R.string.error_fill_fields));
                    return;
                }
                //Check if grade can be parsed to number
                double grade = Double.parseDouble(calculation.getGrade());
                //Check if grade is between the bounds
                if(!isValidGrade(grade)) {
                    mCalculationResultSubject.onNext(getError(R.string.error_grade_out_bounds));
                    return;
                }

                //Accumulate progress
                total += grade * calculation.getWeightValue() / 100;

            }catch (NumberFormatException e){
                mCalculationResultSubject.onNext(getError(R.string.error_number_invalid));
            }
        }

        CalculationResult result;
        String title = String.format(mContext.getString(R.string.text_calculate_result_title),total);
        if(isApproved(total)){
            result = new CalculationResult(title, mContext.getString(R.string.text_approved_msg), total, true);
        }else{
            result = new CalculationResult(title, mContext.getString(R.string.text_not_approved_msg), total, false);
        }

        mCalculationResultSubject.onNext(result);
    }

    private boolean isApproved(double grade){
        return grade >= AppRemoteConfig.getInstance().getMinimumGrade();
    }

    private CalculationResult getError(int stringId){
        return new CalculationResult(mContext.getString(stringId));
    }


}

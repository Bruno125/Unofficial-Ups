package com.brunoaybar.unofficialupc.data.models;

import java.util.List;

/**
 * Immutable model class for a Course.
 */
public final class Course {

    private String mCode;
    private String mName;
    private String mFormula;
    private double mCurrentProgress;
    private double mCurrentGrade;
    private List<Assessment> mAssesments;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        this.mCode = code;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getFormula() {
        return mFormula;
    }

    public void setFormula(String formula) {
        this.mFormula = formula;
    }

    public double getCurrentProgress() {
        return mCurrentProgress;
    }

    public void setCurrentProgress(double currentProgress) {
        this.mCurrentProgress = currentProgress;
    }

    public double getCurrentGrade() {
        return mCurrentGrade;
    }

    public void setCurrentGrade(double currentGrade) {
        this.mCurrentGrade = currentGrade;
    }

    public List<Assessment> getAssesments() {
        return mAssesments;
    }

    public void setAssesments(List<Assessment> assesments) {
        this.mAssesments = assesments;
    }

}

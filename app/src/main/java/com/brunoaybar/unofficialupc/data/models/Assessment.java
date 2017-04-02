package com.brunoaybar.unofficialupc.data.models;

/**
 * Model class for course assessments
 */

public class Assessment {
    private String mCode;
    private String mName;
    private String mNickname;
    private String mIndex;
    private double mWeight;
    private String mGrade;

    public Assessment() {
    }

    public Assessment(String mCode, String mName, String mNickname, String mIndex, double mWeight, String mGrade) {
        this.mCode = mCode;
        this.mName = mName;
        this.mNickname = mNickname;
        this.mIndex = mIndex;
        this.mWeight = mWeight;
        this.mGrade = mGrade;
    }

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

    public String getNickname() {
        return mNickname;
    }

    public void setNickname(String nickname) {
        this.mNickname = nickname;
    }

    public String getIndex() {
        return mIndex;
    }

    public void setIndex(String index) {
        this.mIndex = index;
    }

    public double getWeight() {
        return mWeight;
    }

    public void setWeight(double weight) {
        this.mWeight = weight;
    }

    public String getGrade() {
        return mGrade;
    }

    public void setGrade(String grade) {
        this.mGrade = grade;
    }
}
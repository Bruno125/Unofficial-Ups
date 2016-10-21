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
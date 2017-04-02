package com.brunoaybar.unofficialupc.data.models;

/**
 * Created by brunoaybar on 16/10/2016.
 */

public class Absence {

    private String code;
    private String courseCode;
    private String courseName;
    private int total;
    private int maximum;

    public Absence() {
    }

    public Absence(String code, String courseCode, String courseName, int total, int maximum) {
        this.code = code;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.total = total;
        this.maximum = maximum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }
}

package com.brunoaybar.unofficialupc.data.models;

import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Immutable model class for a student weekly Timetable
 */
public final class Timetable {

    private Map<String,Day> mDays;

    public Timetable(){
        mDays = new HashMap<>();
    }

    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

    public void addDay(Day day){
        mDays.put(dateFormatter.format(day.getDate()),day);
    }

    public Day getDay(Date date){
        String dateDesc = dateFormatter.format(date);
        return mDays.get(dateDesc);
    }

    public List<Day> getDaysList(){
        return new ArrayList<>(mDays.values());
    }

    public static class Day{
        private int mCode;
        private Date mDate;
        private List<Class> mClasses;

        public Day() {
            mClasses = new ArrayList<>();
        }

        public int getCode() {
            return mCode;
        }

        public void setCode(int mCode) {
            this.mCode = mCode;
        }

        public Date getDate() {
            return mDate;
        }

        public void setDate(Date date) {
            this.mDate = date;
        }

        public List<Class> getClasses() {
            return mClasses;
        }

        public void setClasses(List<Class> mClasses) {
            this.mClasses = mClasses;
        }

    }

    public static class Class{
        private String mCourseCode;
        private String mCourseName;
        private String mCourseShortName;
        private Date mDate;
        private String mVenue;
        private String mSection;
        private String mRoom;
        private int mDuration;

        public Class() {
        }

        public Class(String mCourseCode, String mCourseName, String mCourseShortName, Date mDate, String mVenue, String mSection, String mRoom, int mDuration) {
            this.mCourseCode = mCourseCode;
            this.mCourseName = mCourseName;
            this.mCourseShortName = mCourseShortName;
            this.mDate = mDate;
            this.mVenue = mVenue;
            this.mSection = mSection;
            this.mRoom = mRoom;
            this.mDuration = mDuration;
        }

        public String getCourseName() {
            return mCourseName;
        }

        public void setCourseName(String mCourseName) {
            this.mCourseName = mCourseName;
        }

        public String getCourseShortName() {
            return mCourseShortName;
        }

        public void setCourseShortName(String mCourseShortName) {
            this.mCourseShortName = mCourseShortName;
        }

        public Date getDate() {
            return mDate;
        }

        public void setDate(Date mDate) {
            this.mDate = mDate;
        }

        public String getVenue() {
            return mVenue;
        }

        public void setVenue(String venue) {
            this.mVenue = venue;
        }

        public String getSection() {
            return mSection;
        }

        public void setSection(String section) {
            this.mSection = section;
        }

        public String getRoom() {
            return mRoom;
        }

        public void setRoom(String room) {
            this.mRoom = room;
        }

        public int getDuration() {
            return mDuration;
        }

        public void setDuration(int mDuration) {
            this.mDuration = mDuration;
        }

        public String getCourseCode() {
            return mCourseCode;
        }

        public void setCourseCode(String mCodeCourse) {
            this.mCourseCode = mCodeCourse;
        }
    }

}

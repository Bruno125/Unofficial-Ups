package com.brunoaybar.unofficalupc.data.models;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Immutable model class for a student weekly Timetable
 */
public final class Timetable {

    @NonNull Map<Integer,Day> mDays;

    public Map<Integer,Day> getDays(){
        return mDays;
    }

    public void setDays(Map<Integer,Day> days){
        this.mDays = days;
    }

    public class Day{
        @NonNull private int mCode;
        @NonNull private Date mDate;
        @NonNull  private List<Class> mClasses;

        public int getCode() {
            return mCode;
        }

        public void setCode(int mCode) {
            this.mCode = mCode;
        }

        public Date getDate() {
            return mDate;
        }

        public void setDate(Date mDate) {
            this.mDate = mDate;
        }

        public List<Class> getClasses() {
            return mClasses;
        }

        public void setClasses(List<Class> mClasses) {
            this.mClasses = mClasses;
        }
    }

    public class Class{
        private String mCode;
        private String mCourseName;
        private String mCourseShortName;
        private Date mDate;
        private String mVenue;
        private String mSection;
        private String mRoom;

        public String getCode() {
            return mCode;
        }

        public void setCode(String mCode) {
            this.mCode = mCode;
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
    }

}

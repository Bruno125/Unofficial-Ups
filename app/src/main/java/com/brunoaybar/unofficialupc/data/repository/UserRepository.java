package com.brunoaybar.unofficialupc.data.repository;

import com.brunoaybar.unofficialupc.data.models.Absence;
import com.brunoaybar.unofficialupc.data.models.Classmate;
import com.brunoaybar.unofficialupc.data.models.Course;
import com.brunoaybar.unofficialupc.data.models.Timetable;

import java.util.List;

import rx.Observable;

/**
 * Created by brunoaybar on 11/03/2017.
 */

public interface UserRepository {
    Observable<Timetable> getTimeTable();
    Observable<List<Course>> getCourses();
    Observable<Course> getCourseDetail(String courseCode);
    Observable<List<Absence>> getAbsences();
    Observable<List<Classmate>> getClassmates(String courseCode);
}

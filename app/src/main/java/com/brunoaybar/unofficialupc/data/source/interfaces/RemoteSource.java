package com.brunoaybar.unofficialupc.data.source.interfaces;

import com.brunoaybar.unofficialupc.data.models.Absence;
import com.brunoaybar.unofficialupc.data.models.Classmate;
import com.brunoaybar.unofficialupc.data.models.Course;
import com.brunoaybar.unofficialupc.data.models.Timetable;
import com.brunoaybar.unofficialupc.data.models.User;

import java.util.List;

import rx.Observable;

/**
 * Created by brunoaybar on 11/03/2017.
 */

public interface RemoteSource {
    Observable<Boolean> validateToken(String userCode, String token);
    Observable<User> login(String user, String password);
    Observable<Timetable> getTimeTable(String userCode, String token);
    Observable<List<Course>> getCourses(String userCode, String token);
    Observable<Course> getCourseDetail(String courseCode, String userCode, String token);
    Observable<List<Absence>> getAbsences(String userCode, String token);
    Observable<List<Classmate>> getClassmates(String courseCode, String userCode, String token);
}

package com.brunoaybar.unofficialupc.data.repository.impl;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.models.Absence;
import com.brunoaybar.unofficialupc.data.models.Classmate;
import com.brunoaybar.unofficialupc.data.models.Course;
import com.brunoaybar.unofficialupc.data.models.Timetable;
import com.brunoaybar.unofficialupc.data.repository.SessionRepository;
import com.brunoaybar.unofficialupc.data.repository.UserRepository;
import com.brunoaybar.unofficialupc.data.source.interfaces.RemoteDao;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by brunoaybar on 11/03/2017.
 */

public class UpcUserRepository implements UserRepository{

    @Inject
    RemoteDao remoteDao;
    @Inject
    SessionRepository sessionRepo;

    public UpcUserRepository() {
        UpcApplication.getDataComponent().inject(this);
    }

    @Override
    public Observable<Timetable> getTimeTable() {
        return sessionRepo.getSession().flatMap(s -> remoteDao.getTimeTable(s.getUserCode(),s.getToken()));
    }

    @Override
    public Observable<List<Course>> getCourses() {
        return sessionRepo.getSession()
                .flatMap(s -> remoteDao.getCourses(s.getUserCode(),s.getToken())) //get all courses
                .flatMap(Observable::from).flatMap(course -> getCourseDetail(course.getCode())) //get detail for each course
                .toList();  //convert back to list
    }

    @Override
    public Observable<Course> getCourseDetail(String courseCode) {
        return sessionRepo.getSession().flatMap(s -> remoteDao.getCourseDetail(courseCode,s.getUserCode(),s.getToken()));
    }

    @Override
    public Observable<List<Absence>> getAbsences() {
        return sessionRepo.getSession().flatMap(s -> remoteDao.getAbsences(s.getUserCode(),s.getToken()));
    }

    @Override
    public Observable<List<Classmate>> getClassmates(String courseCode) {
        return sessionRepo.getSession().flatMap(s -> remoteDao.getClassmates(courseCode,s.getUserCode(),s.getToken()));
    }
}

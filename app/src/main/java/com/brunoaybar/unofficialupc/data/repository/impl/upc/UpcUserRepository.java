package com.brunoaybar.unofficialupc.data.repository.impl.upc;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.models.Absence;
import com.brunoaybar.unofficialupc.data.models.Assessment;
import com.brunoaybar.unofficialupc.data.models.Classmate;
import com.brunoaybar.unofficialupc.data.models.Course;
import com.brunoaybar.unofficialupc.data.models.Timetable;
import com.brunoaybar.unofficialupc.data.repository.SessionRepository;
import com.brunoaybar.unofficialupc.data.repository.UserRepository;
import com.brunoaybar.unofficialupc.data.source.interfaces.RemoteSource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by brunoaybar on 11/03/2017.
 */

public class UpcUserRepository implements UserRepository{

    @Inject RemoteSource remoteSource;
    @Inject SessionRepository sessionRepo;

    public UpcUserRepository() {
        UpcApplication.getDataComponent().inject(this);
    }

    @Override
    public Observable<Timetable> getTimeTable() {
        return sessionRepo.getSession()
                .flatMap(s -> remoteSource.getTimeTable(s.getUserCode(),s.getToken()))
                .map(this::joinConsecutiveClasses);
    }

    private Timetable joinConsecutiveClasses(Timetable timetable){
        List<Timetable.Day> days = timetable.getDaysList();
        if(days == null)
            days = new ArrayList<>();
        for(Timetable.Day day : days){
            if(day == null)
                continue;
            List<Timetable.Class> classes = day.getClasses();
            if(classes == null)
                continue;
            List<Timetable.Class> newClasses = new ArrayList<>();
            boolean skipLast = false;
            for(int i= 0; i<classes.size()-1;i++){
                Timetable.Class pClass = classes.get(i);
                if(pClass == null)
                    continue;
                if(pClass.getDuration() == 1){
                    Timetable.Class nextClass = classes.get(i+1);
                    //Only try to join if classes are on same room
                    if(pClass.getRoom().equals(nextClass.getRoom())){
                        //Compare that classes are consecutive
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(pClass.getDate());
                        calendar.add(Calendar.HOUR,pClass.getDuration());
                        int expectedFinishTime = calendar.get(Calendar.HOUR);
                        calendar.setTime(nextClass.getDate());
                        int nextClassStartTime = calendar.get(Calendar.HOUR);
                        if(expectedFinishTime == nextClassStartTime){
                            pClass.setDuration(pClass.getDuration() + nextClass.getDuration());
                            i++;
                            if(i==classes.size()-1)
                                skipLast = true;
                        }
                    }
                }
                newClasses.add(pClass);
            }
            if(classes.size()>0 && !skipLast){
                newClasses.add(classes.get(classes.size()-1));
            }


            day.setClasses(newClasses);
        }
        return timetable;
    }

    @Override
    public Observable<List<Course>> getCourses() {
        return sessionRepo.getSession()
                .flatMap(s -> remoteSource.getCourses(s.getUserCode(),s.getToken())) //get all courses
                .flatMap(Observable::from).flatMap(course -> getCourseDetail(course.getCode())) //get detail for each course
                .toList();  //convert back to list
    }

    @Override
    public Observable<Course> getCourseDetail(String courseCode) {
        return sessionRepo.getSession().flatMap(s -> remoteSource.getCourseDetail(courseCode,s.getUserCode(),s.getToken()));
    }

    @Override
    public Observable<List<Absence>> getAbsences() {
        return sessionRepo.getSession().flatMap(s -> remoteSource.getAbsences(s.getUserCode(),s.getToken()));
    }

    @Override
    public Observable<List<Classmate>> getClassmates(String courseCode) {
        return sessionRepo.getSession().flatMap(s -> remoteSource.getClassmates(courseCode,s.getUserCode(),s.getToken()));
    }
}

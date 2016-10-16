package com.brunoaybar.unofficalupc.modules.courses;

import com.brunoaybar.unofficalupc.data.models.Course;
import com.brunoaybar.unofficalupc.data.models.Timetable;
import com.brunoaybar.unofficalupc.data.models.User;
import com.brunoaybar.unofficalupc.data.source.UpcRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by brunoaybar on 16/10/2016.
 */

public class CoursesViewModel {

    private UpcRepository mRepository;

    public CoursesViewModel(UpcRepository repository) {
        mRepository = repository;
    }

    public Observable<Course> getCoursesStream(){
        return Observable.create(subscriber ->
                //Get current session
                mRepository.getSession().subscribe(user -> {
                    //Use current session to get courses
                    mRepository.getCourses(user)
                            .flatMap(Observable::from)
                            .subscribe(subscriber::onNext,subscriber::onError);
                }));

    }

}

package com.brunoaybar.unofficalupc.modules.courses;

import android.os.Bundle;
import android.text.TextUtils;

import com.brunoaybar.unofficalupc.data.models.Course;
import com.brunoaybar.unofficalupc.data.models.Timetable;
import com.brunoaybar.unofficalupc.data.models.User;
import com.brunoaybar.unofficalupc.data.source.UpcRepository;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by brunoaybar on 16/10/2016.
 */

public class CoursesViewModel {

    private UpcRepository mRepository;

    public CoursesViewModel(UpcRepository repository) {
        mRepository = repository;
    }

    private BehaviorSubject<List<Course>> mCoursesSubject;
    public Observable<List<Course>> getCoursesStream(){
        if(mCoursesSubject == null){
            mCoursesSubject = BehaviorSubject.create();
            obtainCoursesStream();
        }
        return mCoursesSubject.asObservable();
    }

    private void obtainCoursesStream(){
        mRepository.getSession().subscribe(user -> {
            //Use current session to get courses
            mRepository.getCourses(user)
                    .flatMap(courses -> Observable.from(courses)
                            .flatMap(course ->mRepository.getCourseDetail(user,course.getCode())).toList())
                    //.flatMapIterable(courses -> courses)
                    .subscribe(course ->{
                        mCoursesSubject.onNext(course);
                    },mCoursesSubject::onError);
        });

    }

    private static final String PARAM_COURSE = "courseJson";
    private BehaviorSubject<Bundle> mCourseBundleSubject;
    public Observable<Bundle> getCourseDetails(){
        mCourseBundleSubject = BehaviorSubject.create();
        return mCourseBundleSubject.asObservable();
    }

    public void courseAvailable(Course course){
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_COURSE,new Gson().toJson(course));
        mCourseBundleSubject.onNext(bundle);
        mCourseBundleSubject.onCompleted();
    }

    public Observable<Course> getCourseFromBundle(Bundle bundle){
        Course course = new Gson().fromJson(bundle.getString(PARAM_COURSE),Course.class);
        return Observable.just(course)
                .filter(c -> !TextUtils.isEmpty(course.getName()))
                .filter(c -> !TextUtils.isEmpty(course.getFormula()))
                .filter(c -> course.getAssesments().size()>0)
                .first();
    }



}

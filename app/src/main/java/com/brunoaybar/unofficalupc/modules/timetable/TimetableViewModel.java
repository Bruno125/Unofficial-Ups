package com.brunoaybar.unofficalupc.modules.timetable;

import android.support.annotation.NonNull;

import com.brunoaybar.unofficalupc.data.models.Course;
import com.brunoaybar.unofficalupc.data.models.Timetable;
import com.brunoaybar.unofficalupc.data.models.User;
import com.brunoaybar.unofficalupc.data.source.UpcRepository;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * View Model for the TimetableFragment
 */

public class TimetableViewModel {

    @NonNull
    private UpcRepository mRepository;

    public TimetableViewModel(@NonNull UpcRepository repository){
        mRepository = repository;
    }

    public Observable<Timetable> getTimetable(){
        return Observable.create(subscriber ->
                //Get current session
                mRepository.getSession().subscribe(user -> {
                    //Use current session to get timetable
                    mRepository.getTimeTable(user.getUserCode(),user.getToken())
                            .subscribe( response ->{
                                subscriber.onNext(response);
                                subscriber.onCompleted();
                            },subscriber::onError);
                }));
    }

    public Observable<Course> getCourseFromClass(Timetable.Class mClass){
        Course course = new Course();
        course.setCode(mClass.getCourseCode());
        course.setName(mClass.getCourseShortName());
        return Observable.just(course);
    }
}

package com.brunoaybar.unofficialupc.modules.timetable;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.models.Course;
import com.brunoaybar.unofficialupc.data.models.Timetable;
import com.brunoaybar.unofficialupc.data.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * View Model for the TimetableFragment
 */

public class TimetableViewModel {

    @Inject
    UserRepository mRepository;

    public TimetableViewModel(){
        UpcApplication.getViewModelsComponent().inject(this);
    }

    public Observable<Timetable> getTimetable(){
        return mRepository.getTimeTable();
    }

    public Observable<Course> getCourseFromClass(Timetable.Class mClass){
        Course course = new Course();
        course.setCode(mClass.getCourseCode());
        course.setName(mClass.getCourseShortName());
        return Observable.just(course);
    }
}

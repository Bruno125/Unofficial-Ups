package com.brunoaybar.unofficialupc.modules.courses;

import android.os.Bundle;
import android.text.TextUtils;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.models.Course;
import com.brunoaybar.unofficialupc.data.models.errors.NoInternetException;
import com.brunoaybar.unofficialupc.data.repository.UserRepository;
import com.brunoaybar.unofficialupc.utils.Utils;
import com.brunoaybar.unofficialupc.utils.interfaces.StringProvider;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by brunoaybar on 16/10/2016.
 */

public class CoursesViewModel {

    @Inject
    UserRepository mRepository;

    @Inject
    StringProvider stringProvider;

    public CoursesViewModel() {
        UpcApplication.getViewModelsComponent().inject(this);
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
        mRepository.getCourses().subscribe(mCoursesSubject::onNext,e -> {
            mCoursesSubject.onError(Utils.getError(e,stringProvider.getString(R.string.error_getting_courses)));
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
        // If bundle is null, we can't get course info
        if(bundle==null)
            return Observable.error(new Throwable());
        // Parse course info from bundle
        Course course = new Gson().fromJson(bundle.getString(PARAM_COURSE),Course.class);
        // If code is not available, we can do nothing
        if(TextUtils.isEmpty(course.getCode()))
            return Observable.error(new Throwable());
        // If we have the course code but no assessments, we get the course details
        // from the remote repo and return that value
        if(course.getAssesments()==null || course.getAssesments().size()<1){
            return mRepository.getCourseDetail(course.getCode());
        //But if we already have that information, we just return the course
        }else{
            return Observable.just(course);
        }
    }



}

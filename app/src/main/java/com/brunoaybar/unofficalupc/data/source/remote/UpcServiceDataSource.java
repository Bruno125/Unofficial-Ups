package com.brunoaybar.unofficalupc.data.source.remote;

import android.text.TextUtils;

import com.brunoaybar.unofficalupc.data.models.Absence;
import com.brunoaybar.unofficalupc.data.models.Classmate;
import com.brunoaybar.unofficalupc.data.models.Course;
import com.brunoaybar.unofficalupc.data.models.Timetable;
import com.brunoaybar.unofficalupc.data.models.User;
import com.brunoaybar.unofficalupc.data.source.remote.responses.AbsencesResponse;
import com.brunoaybar.unofficalupc.data.source.remote.requests.LoginRequest;
import com.brunoaybar.unofficalupc.data.source.remote.responses.ClassmatesResponse;
import com.brunoaybar.unofficalupc.data.source.remote.responses.CourseListResponse;
import com.brunoaybar.unofficalupc.data.source.remote.responses.CourseResponse;
import com.brunoaybar.unofficalupc.data.source.remote.responses.ServiceException;
import com.brunoaybar.unofficalupc.data.source.remote.responses.TimetableResponse;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by brunoaybar on 13/10/2016.
 */

public class UpcServiceDataSource{

    private static UpcServiceDataSource INSTANCE;
    private UpcServiceInterface mService;

    private UpcServiceDataSource(){}
    public static UpcServiceDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new UpcServiceDataSource();
            INSTANCE.mService = UpcServiceFactory.createDefaultRetrofitService();
        }
        return INSTANCE;
    }

    public Observable<Boolean> validateToken(String userCode, String token){
        if(TextUtils.isEmpty(userCode) || TextUtils.isEmpty(token))
            return Observable.just(false);
        return mService.getCourses(userCode,token)
                .subscribeOn(Schedulers.newThread())
                .map( r -> true);
    }

    public Observable<User> login(String user, String password) {
        return mService.login(new LoginRequest(user, password,"A"))
                .subscribeOn(Schedulers.newThread())
                .map(response -> {
                    if(!response.isError()) {
                        User userResponse = response.transform();
                        userResponse.setSavedPassword(password);
                        return userResponse;
                    } else
                        throw new ServiceException(response);
                });
    }

    public Observable<Timetable> getTimeTable(String userCode, String token) {
        return mService.getTimeTable(userCode,token)
                .subscribeOn(Schedulers.newThread())
                .map(TimetableResponse::transform);
    }

    public Observable<List<Course>> getCourses(String userCode, String token) {
        return mService.getCourses(userCode,token)
                .subscribeOn(Schedulers.newThread())
                .map(CourseListResponse::transform);
    }

    public Observable<Course> getCourseDetail(String courseCode, String userCode, String token) {
        return mService.getCourseDetail(courseCode,userCode,token)
                .subscribeOn(Schedulers.newThread())
                .map(CourseResponse::transform);
    }

    public Observable<List<Absence>> getAbsences(String userCode, String token){
        return mService.getAbsences(userCode,token)
                .subscribeOn(Schedulers.newThread())
                .map(AbsencesResponse::transform);
    }

    public Observable<List<Classmate>> getClassmates(String courseCode, User user) {
        return mService.getClassmates(courseCode,user.getUserCode(),user.getToken())
                .subscribeOn(Schedulers.newThread())
                .map(ClassmatesResponse::transform);
    }


}

package com.brunoaybar.unofficalupc.data.source.remote;

import android.text.TextUtils;

import com.brunoaybar.unofficalupc.data.models.Course;
import com.brunoaybar.unofficalupc.data.models.Timetable;
import com.brunoaybar.unofficalupc.data.models.User;
import com.brunoaybar.unofficalupc.data.source.remote.responses.BaseResponse;
import com.brunoaybar.unofficalupc.data.source.remote.requests.LoginRequest;
import com.brunoaybar.unofficalupc.data.source.remote.responses.CoursesResponse;
import com.brunoaybar.unofficalupc.data.source.remote.responses.LoginResponse;
import com.brunoaybar.unofficalupc.data.source.remote.responses.ServiceException;
import com.brunoaybar.unofficalupc.data.source.remote.responses.TimetableResponse;
import com.brunoaybar.unofficalupc.utils.CryptoUtils;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
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
                .map(CoursesResponse::transform);
    }

    public Observable<CoursesResponse> getCourseDetail(String courseCode, String userCode, String token) {
        return null;
    }

}

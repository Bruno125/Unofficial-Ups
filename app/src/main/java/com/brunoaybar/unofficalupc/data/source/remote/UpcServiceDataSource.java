package com.brunoaybar.unofficalupc.data.source.remote;

import com.brunoaybar.unofficalupc.data.models.User;
import com.brunoaybar.unofficalupc.data.source.remote.requests.LoginRequest;
import com.brunoaybar.unofficalupc.data.source.remote.responses.LoginResponse;
import com.brunoaybar.unofficalupc.data.source.remote.responses.CoursesResponse;
import com.brunoaybar.unofficalupc.data.source.remote.responses.TimetableResponse;
import com.brunoaybar.unofficalupc.utils.CryptoUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
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


    public Observable<User> login(String user, String password) {
        return mService.login(new LoginRequest(user, CryptoUtils.encryptPassword(password),"A"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(LoginResponse::transform);
    }

    public Observable<TimetableResponse> getTimeTable(String userCode, String token) {
        return null;
    }

    public Observable<CoursesResponse> getCourses(String userCode, String token) {
        return null;
    }

    public Observable<CoursesResponse> getCourseDetail(String courseCode, String userCode, String token) {
        return null;
    }
}

package com.brunoaybar.unofficialupc.data.source.remote;

import android.util.Log;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.models.Absence;
import com.brunoaybar.unofficialupc.data.models.Classmate;
import com.brunoaybar.unofficialupc.data.models.Course;
import com.brunoaybar.unofficialupc.data.models.Payment;
import com.brunoaybar.unofficialupc.data.models.Timetable;
import com.brunoaybar.unofficialupc.data.models.User;
import com.brunoaybar.unofficialupc.data.models.errors.NoInternetException;
import com.brunoaybar.unofficialupc.data.source.remote.responses.PaymentsResponse;
import com.brunoaybar.unofficialupc.utils.interfaces.InternetVerifier;
import com.brunoaybar.unofficialupc.data.source.interfaces.RemoteSource;
import com.brunoaybar.unofficialupc.data.source.remote.responses.AbsencesResponse;
import com.brunoaybar.unofficialupc.data.source.remote.requests.LoginRequest;
import com.brunoaybar.unofficialupc.data.source.remote.responses.ClassmatesResponse;
import com.brunoaybar.unofficialupc.data.source.remote.responses.CourseListResponse;
import com.brunoaybar.unofficialupc.data.source.remote.responses.CourseResponse;
import com.brunoaybar.unofficialupc.data.source.remote.responses.ServiceException;
import com.brunoaybar.unofficialupc.data.source.remote.responses.TimetableResponse;
import com.brunoaybar.unofficialupc.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by brunoaybar on 13/10/2016.
 */

public class UpcServiceDataSource implements RemoteSource {

    private UpcServiceInterface mService;

    @Inject InternetVerifier internetVerifier;

    public UpcServiceDataSource(){
        mService = UpcServiceFactory.createDefaultRetrofitService();
        UpcApplication.getDataComponent().inject(this);
    }
    /**
     * Check if token is available, and is still working
     * @param userCode
     * @param token
     * @return
     */
    public Observable<Boolean> validateToken(String userCode, String token){
        if(Utils.isEmpty(userCode) || Utils.isEmpty(token))
            return Observable.just(false);

        if(!internetVerifier.isConnected()){
            return Observable.error(new NoInternetException());
        }
        return mService.getCourses(userCode,token)
                .subscribeOn(Schedulers.newThread())
                .map( r -> !r.isError());
    }

    public Observable<User> login(String user, String password) {
        if(!internetVerifier.isConnected()){
            return Observable.error(new NoInternetException());
        }
        return mService.login(new LoginRequest(user, password,"A"))
                .subscribeOn(Schedulers.newThread())
                .map(response -> {
                    if(!response.isError()) {
                        User userResponse = response.transform();
                        Log.i("UNOFFICIAL UPC","Token is : " + userResponse.getToken());
                        userResponse.setSavedPassword(password);
                        return userResponse;
                    } else
                        throw new ServiceException(response);
                });
    }

    public Observable<Timetable> getTimeTable(String userCode, String token) {
        if(!internetVerifier.isConnected()){
            return Observable.error(new NoInternetException());
        }
        return mService.getTimeTable(userCode,token)
                .subscribeOn(Schedulers.newThread())
                .map(TimetableResponse::transform);
    }

    public Observable<List<Course>> getCourses(String userCode, String token) {
        if(!internetVerifier.isConnected()){
            return Observable.error(new NoInternetException());
        }
        return mService.getCourses(userCode,token)
                .subscribeOn(Schedulers.newThread())
                .map(CourseListResponse::transform);
    }

    public Observable<Course> getCourseDetail(String courseCode, String userCode, String token) {
        if(!internetVerifier.isConnected()){
            return Observable.error(new NoInternetException());
        }
        return mService.getCourseDetail(courseCode,userCode,token)
                .subscribeOn(Schedulers.newThread())
                .map(CourseResponse::transform);
    }

    public Observable<List<Absence>> getAbsences(String userCode, String token){
        if(!internetVerifier.isConnected()){
            return Observable.error(new NoInternetException());
        }
        return mService.getAbsences(userCode,token)
                .subscribeOn(Schedulers.newThread())
                .map(AbsencesResponse::transform);
    }

    public Observable<List<Classmate>> getClassmates(String courseCode, String userCode, String token) {
        if(!internetVerifier.isConnected()){
            return Observable.error(new NoInternetException());
        }
        return mService.getClassmates(courseCode,userCode,token)
                .subscribeOn(Schedulers.newThread())
                .map(ClassmatesResponse::transform);
    }

    @Override
    public Observable<List<Payment>> getPayments(String userCode, String token) {
        if(!internetVerifier.isConnected()){
            return Observable.error(new NoInternetException());
        }
        return mService.getPayments(userCode,token)
                .subscribeOn(Schedulers.newThread())
                .map(PaymentsResponse::transform);
    }

}

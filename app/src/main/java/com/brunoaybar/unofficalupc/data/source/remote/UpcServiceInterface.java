package com.brunoaybar.unofficalupc.data.source.remote;

import com.brunoaybar.unofficalupc.data.source.remote.requests.LoginRequest;
import com.brunoaybar.unofficalupc.data.source.remote.responses.AuthResponse;
import com.brunoaybar.unofficalupc.data.source.remote.responses.CoursesResponse;
import com.brunoaybar.unofficalupc.data.source.remote.responses.TimetableResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by brunoaybar on 13/10/2016.
 */

interface UpcServiceInterface {

    @POST("Autenticarp2")
    @Headers("Content-Type: application/json")
    Observable<AuthResponse> login(@Body LoginRequest request);

    @GET("Horario/?CodAlumno/")
    Observable<TimetableResponse> getTimeTable(@Query("userCode") String userCode,
                                               @Query("token") String token);


    @GET("CursoAlumno/")
    Observable<CoursesResponse> getCourses(@Query("userCode") String userCode,
                                           @Query("token") String token);


    @GET("Nota/")
    Observable<CoursesResponse> getCourseDetail(@Query("courseCode") String courseCode,
                                                @Query("userCode") String userCode,
                                                @Query("token") String token);

}

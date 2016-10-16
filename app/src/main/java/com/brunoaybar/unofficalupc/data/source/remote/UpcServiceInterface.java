package com.brunoaybar.unofficalupc.data.source.remote;

import com.brunoaybar.unofficalupc.data.source.remote.requests.LoginRequest;
import com.brunoaybar.unofficalupc.data.source.remote.responses.LoginResponse;
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
    Observable<LoginResponse> login(@Body LoginRequest request);

    @GET("Horario/")
    Observable<TimetableResponse> getTimeTable(@Query("CodAlumno") String userCode,
                                               @Query("Token") String token);


    @GET("CursoAlumno/")
    Observable<CoursesResponse> getCourses(@Query("CodAlumno") String userCode,
                                           @Query("Token") String token);


    @GET("Nota/")
    Observable<CoursesResponse> getCourseDetail(@Query("CodCurso") String courseCode,
                                                @Query("CodAlumno") String userCode,
                                                @Query("Token") String token);

}

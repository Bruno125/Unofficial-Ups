package com.brunoaybar.unofficialupc.data.source.remote;

import com.brunoaybar.unofficialupc.data.source.remote.requests.LoginRequest;
import com.brunoaybar.unofficialupc.data.source.remote.responses.AbsencesResponse;
import com.brunoaybar.unofficialupc.data.source.remote.responses.ClassmatesResponse;
import com.brunoaybar.unofficialupc.data.source.remote.responses.CourseListResponse;
import com.brunoaybar.unofficialupc.data.source.remote.responses.LoginResponse;
import com.brunoaybar.unofficialupc.data.source.remote.responses.CourseResponse;
import com.brunoaybar.unofficialupc.data.source.remote.responses.PaymentsResponse;
import com.brunoaybar.unofficialupc.data.source.remote.responses.ReserveAvailabilityResponse;
import com.brunoaybar.unofficialupc.data.source.remote.responses.ReserveResponse;
import com.brunoaybar.unofficialupc.data.source.remote.responses.TimetableResponse;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
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
    Observable<CourseListResponse> getCourses(@Query("CodAlumno") String userCode,
                                              @Query("Token") String token);

    @GET("Nota/")
    Observable<CourseResponse> getCourseDetail(@Query("CodCurso") String courseCode,
                                               @Query("CodAlumno") String userCode,
                                               @Query("Token") String token);

    @GET("Inasistencia/")
    Observable<AbsencesResponse> getAbsences(@Query("CodAlumno") String userCode,
                                                 @Query("Token") String token);

    @GET("Companeros/")
    Observable<ClassmatesResponse> getClassmates(@Query("CodCurso") String courseCode,
                                                 @Query("CodAlumno") String userCode,
                                                 @Query("Token") String token);

    @GET("PagoPendiente/")
    Observable<PaymentsResponse> getPayments(@Query("CodAlumno") String userCode,
                                             @Query("Token") String token);

    @GET("RecursosDisponible/")
    Observable<ReserveAvailabilityResponse> getReservesAvailability(@QueryMap Map<String,String> filters,
                                                                    @Query("CodAlumno") String userCode,
                                                                    @Query("Token") String token);

    @GET("Reservar/")
    Observable<ReserveResponse> reserveResource(@Query("CodRecurso") String resourceCode,
                                                @Query("NomRecurso") String resourceName,
                                                @Query("FecIni") String startDate,
                                                @Query("FecFin") String endDate,
                                                @Query("CanHoras") String amountHours,
                                                @Query("CodAlumno") String userCode,
                                                @Query("Token") String token);
}

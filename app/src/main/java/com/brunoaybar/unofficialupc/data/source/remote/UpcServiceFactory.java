package com.brunoaybar.unofficialupc.data.source.remote;

import com.brunoaybar.unofficialupc.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by brunoaybar on 13/10/2016.
 */

class UpcServiceFactory {

    public static UpcServiceInterface createDefaultRetrofitService(){
        return createRetrofitService(UpcServiceInterface.class);
    }

    public static <T> T createRetrofitService(final Class<T> clazz){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BuildConfig.REST_URL)
                .build();

        return retrofit.create(clazz);

    }

}

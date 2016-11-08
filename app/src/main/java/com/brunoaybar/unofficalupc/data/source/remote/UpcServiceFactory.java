package com.brunoaybar.unofficalupc.data.source.remote;

import com.brunoaybar.unofficalupc.BuildConfig;

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
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.REST_URL)
                .build();

        return retrofit.create(clazz);

    }

}

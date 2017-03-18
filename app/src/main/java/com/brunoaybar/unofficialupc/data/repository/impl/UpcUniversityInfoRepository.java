package com.brunoaybar.unofficialupc.data.repository.impl;

import com.brunoaybar.unofficialupc.analytics.AppRemoteConfig;
import com.brunoaybar.unofficialupc.data.models.ReserveFilter;
import com.brunoaybar.unofficialupc.data.repository.UniversityInfoRepository;
import com.brunoaybar.unofficialupc.data.source.injection.BaseDataComponent;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by brunoaybar on 18/03/2017.
 */

public class UpcUniversityInfoRepository implements UniversityInfoRepository{

    @Inject
    AppRemoteConfig remoteConfig;

    public UpcUniversityInfoRepository(BaseDataComponent dataComponent){
        dataComponent.inject(this);
    }

    @Override
    public Observable<List<ReserveFilter>> getReserveFilters(){
        return remoteConfig.getReserveInfo()
                .map(json -> new Gson().fromJson(json,ReserveFilter[].class))
                .map(Arrays::asList);
    }
}

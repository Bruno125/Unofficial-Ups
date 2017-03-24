package com.brunoaybar.unofficialupc.data.repository.impl;

import com.brunoaybar.unofficialupc.analytics.AppRemoteConfig;
import com.brunoaybar.unofficialupc.data.models.ReserveFilter;
import com.brunoaybar.unofficialupc.data.models.ReserveOption;
import com.brunoaybar.unofficialupc.data.repository.SessionRepository;
import com.brunoaybar.unofficialupc.data.repository.UniversityInfoRepository;
import com.brunoaybar.unofficialupc.data.source.injection.BaseDataComponent;
import com.brunoaybar.unofficialupc.data.source.interfaces.RemoteSource;
import com.brunoaybar.unofficialupc.utils.interfaces.DateProvider;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by brunoaybar on 18/03/2017.
 */

public class UpcUniversityInfoRepository implements UniversityInfoRepository{

    @Inject
    SessionRepository sessionRepo;

    @Inject
    RemoteSource remoteSource;

    @Inject
    AppRemoteConfig remoteConfig;

    @Inject
    DateProvider dateProvider;

    public UpcUniversityInfoRepository(BaseDataComponent dataComponent){
        dataComponent.inject(this);
    }

    @Override
    public Observable<List<ReserveFilter>> getReserveFilters(){
        return remoteConfig.getReserveInfo()
                .map(json -> new Gson().fromJson(json,ReserveFilter[].class))
                .map(Arrays::asList)
                .flatMap(Observable::from)
                .map( filter -> {
                    if(filter.isCustom()){
                        filter = setupCustomFilter(filter);
                    }
                    return filter;
                }).toList();
    }

    @Override
    public Observable<List<ReserveOption>> getReserveOptions(List<ReserveFilter> filters) {
        return sessionRepo.getSession()
                .flatMap(s -> remoteSource.getReserveOptions(filters,s.getUserCode(),s.getToken()));
    }

    private static final String KEY_DATE_FILTER = "dates";
    private static final int AMOUNT_OF_DAYS = 7;
    private ReserveFilter setupCustomFilter(ReserveFilter filter){
        if (filter.getKey().equals(KEY_DATE_FILTER)){
            //Add next 7 days to values
            Calendar c = Calendar.getInstance();
            SimpleDateFormat codeFormat = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat valueFormat= new SimpleDateFormat("dd/MM/yy");
            List<ReserveFilter.ReserveFilterValue> values = new ArrayList<>();
            Date lastDate = dateProvider.getNow();
            values.add(new ReserveFilter.ReserveFilterValue(codeFormat.format(lastDate),valueFormat.format(lastDate)));
            for(int i=0;i<AMOUNT_OF_DAYS;i++) {
                c.setTime(lastDate);
                c.add(Calendar.DATE,1);
                lastDate = c.getTime();
                values.add(new ReserveFilter.ReserveFilterValue(codeFormat.format(lastDate),valueFormat.format(lastDate)));
            }
            filter.setValues(values);
            return filter;
        }else{
            return filter;
        }
    }




}

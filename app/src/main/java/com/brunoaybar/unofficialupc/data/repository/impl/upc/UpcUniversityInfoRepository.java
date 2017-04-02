package com.brunoaybar.unofficialupc.data.repository.impl.upc;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.analytics.AppRemoteConfig;
import com.brunoaybar.unofficialupc.data.models.ReserveFilter;
import com.brunoaybar.unofficialupc.data.models.ReserveOption;
import com.brunoaybar.unofficialupc.data.repository.SessionRepository;
import com.brunoaybar.unofficialupc.data.repository.UniversityInfoRepository;
import com.brunoaybar.unofficialupc.data.source.injection.BaseDataComponent;
import com.brunoaybar.unofficialupc.data.source.interfaces.RemoteSource;
import com.brunoaybar.unofficialupc.utils.Utils;
import com.brunoaybar.unofficialupc.utils.interfaces.DateProvider;
import com.brunoaybar.unofficialupc.utils.interfaces.StringProvider;
import com.google.gson.Gson;

import java.text.DateFormat;
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

    @Inject
    StringProvider stringProvider;

    public UpcUniversityInfoRepository(BaseDataComponent dataComponent){
        dataComponent.inject(this);
    }

    @Override
    public Observable<List<ReserveFilter>> getReserveFilters(){
        return remoteConfig.getReserveInfo()
                .map(json -> new Gson().fromJson(json,ReserveFilter[].class)).map(Arrays::asList)
                .flatMap(Observable::from)
                .map( filter -> {
                    if(filter.isCustom())
                        filter = setupCustomFilter(filter);
                    return filter;
                }).toList();
    }

    @Override
    public Observable<List<ReserveOption>> getReserveOptions(List<ReserveFilter> filters) {
        List<ReserveFilter> parsedFilters = parseFilters(filters);
        return sessionRepo.getSession()
                .flatMap(s -> remoteSource.getReserveOptions(parsedFilters,s.getUserCode(),s.getToken()));
    }

    @Override
    public Observable<String> reserve(ReserveOption option) {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy HHmm");
        String startDate = dateFormat.format(option.getDatetime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(option.getDatetime());
        calendar.add(Calendar.HOUR,option.getDuration());
        String endDate = dateFormat.format(calendar.getTime());
        String duration = String.valueOf(option.getDuration());

        return sessionRepo.getSession().flatMap( s ->
                remoteSource.reserve(option.getCode(),option.getName(),startDate,endDate,duration,s.getUserCode(),s.getToken()));
    }

    private static final String KEY_DATE_FILTER = "dates";
    private static final int NUMBER_OF_DAYS = 2;
    private ReserveFilter setupCustomFilter(ReserveFilter filter){
        if (filter.getKey().equals(KEY_DATE_FILTER)){
            //Add next 7 days to values
            Calendar c = Calendar.getInstance();
            DateFormat codeFormat = new SimpleDateFormat("ddMMyyyy");
            DateFormat valueFormat= dateProvider.getLocalFormatter(stringProvider.getString(R.string.date_format_reserve));
            List<ReserveFilter.ReserveFilterValue> values = new ArrayList<>();
            Date lastDate = dateProvider.getNow();
            values.add(new ReserveFilter.ReserveFilterValue(codeFormat.format(lastDate),valueFormat.format(lastDate)));
            for(int i=0;i<NUMBER_OF_DAYS;i++) {
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

    private static final String KEY_TIME_FILTER = "times";
    private static final String KEY_START_DATE_PARAM = "FecIni";
    private static final String KEY_END_DATE_PARAM = "FecFin";
    private List<ReserveFilter> parseFilters(List<ReserveFilter> filters){
        List<ReserveFilter> parsedFilters = new ArrayList<>();
        for(ReserveFilter filter : filters){
            ReserveFilter.ReserveFilterValue value = filter.getValues().get(filter.getSelected());
            if(filter.isCustom()){
                if(filter.getKey().equals(KEY_DATE_FILTER)){
                    String selectedDate = filter.getValues().get(filter.getSelected()).getCode();
                    for(ReserveFilter tempFilter : filters){
                        if(tempFilter.getKey().equals(KEY_TIME_FILTER)){
                            ReserveFilter newFilter = new ReserveFilter();
                            newFilter.setKey(KEY_START_DATE_PARAM);
                            newFilter.setServiceKey(KEY_START_DATE_PARAM);
                            String paramValue = selectedDate + " " + tempFilter.getSelectedFilterValue().getCode();
                            List<ReserveFilter.ReserveFilterValue> values = new ArrayList<>();
                            values.add(new ReserveFilter.ReserveFilterValue(paramValue,paramValue));
                            newFilter.setValues(values);
                            parsedFilters.add(newFilter);
                        }
                    }
                }
            }else if(!Utils.isEmpty(filter.getServiceKey())){
                parsedFilters.add(filter);
            }
        }
        return parsedFilters;
    }

}

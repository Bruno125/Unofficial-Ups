package com.brunoaybar.unofficialupc.data.repository;

import com.brunoaybar.unofficialupc.data.models.ReserveFilter;
import com.brunoaybar.unofficialupc.data.models.ReserveOption;

import java.util.List;

import rx.Observable;

/**
 * Created by brunoaybar on 18/03/2017.
 */

public interface UniversityInfoRepository {
    Observable<List<ReserveFilter>> getReserveFilters();
    Observable<List<ReserveOption>> getReserveOptions(List<ReserveFilter> filters);
    Observable<String> reserve(ReserveOption option);
}

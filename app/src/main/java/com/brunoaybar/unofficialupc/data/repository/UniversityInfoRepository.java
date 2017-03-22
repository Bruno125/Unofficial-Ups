package com.brunoaybar.unofficialupc.data.repository;

import com.brunoaybar.unofficialupc.data.models.ReserveFilter;

import java.util.List;

import rx.Observable;

/**
 * Created by brunoaybar on 18/03/2017.
 */

public interface UniversityInfoRepository {
    Observable<List<ReserveFilter>> getReserveFilters();

}

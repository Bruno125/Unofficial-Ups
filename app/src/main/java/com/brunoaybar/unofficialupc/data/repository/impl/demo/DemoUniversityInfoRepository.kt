package com.brunoaybar.unofficialupc.data.repository.impl.demo

import com.brunoaybar.unofficialupc.data.models.ReserveFilter
import com.brunoaybar.unofficialupc.data.models.ReserveOption
import com.brunoaybar.unofficialupc.data.repository.UniversityInfoRepository
import rx.Observable
import java.util.*

class DemoUniversityInfoRepository : UniversityInfoRepository{

    override fun getReserveFilters(): Observable<MutableList<ReserveFilter>> {
        val filters = arrayListOf<ReserveFilter>(
                ReserveFilter("venue","key_venue",false,"Local", arrayListOf(
                        ReserveFilter.ReserveFilterValue("mo","Monterrico"),
                        ReserveFilter.ReserveFilterValue("si","San Isidro"))),
                ReserveFilter("hour","key_hour",false,"Hora", arrayListOf(
                        ReserveFilter.ReserveFilterValue("0700","7:00 am"),
                        ReserveFilter.ReserveFilterValue("0800","8:00 am")))
        )
        return Observable.just(filters)
    }

    override fun getReserveOptions(filters: MutableList<ReserveFilter>?): Observable<MutableList<ReserveOption>> {
        var options = mutableListOf<ReserveOption>()
        for (i in 1..10){
            options.add(ReserveOption("${i}","co","Computadora ${i}","Monterrico", Date(),1))
        }
        return Observable.just(options)
    }

    override fun reserve(option: ReserveOption?): Observable<String> {
        return Observable.just("Reserve satisfactoria")
    }

}
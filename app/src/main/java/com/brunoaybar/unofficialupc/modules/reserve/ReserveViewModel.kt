package com.brunoaybar.unofficialupc.modules.reserve

import com.brunoaybar.unofficialupc.UpcApplication
import com.brunoaybar.unofficialupc.data.models.ReserveFilter
import com.brunoaybar.unofficialupc.data.repository.UniversityInfoRepository
import rx.Observable
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject
import javax.inject.Inject

class ReserveViewModel {

    @Inject
    lateinit var repository: UniversityInfoRepository

    init {
        UpcApplication.getViewModelsComponent().inject(this)
    }

    fun load(){
        repository.reserveFilters.flatMap { Observable.from(it) }
                .filter { !it.values.isEmpty() }.toList()
                .subscribe ({ info ->
                    when (info.isEmpty()){
                        false -> reserveFiltersSubject.onNext(info.map(::DisplayableReserveFilter))
                        true -> reserveFiltersSubject.onError(Throwable())
                    }
                }, { reserveEnabledSubject.onError(it)})
    }

    private val reserveFiltersSubject : BehaviorSubject<List<DisplayableReserveFilter>> = BehaviorSubject.create()
    fun getReserveEntries() : Observable<List<DisplayableReserveFilter>>{
        return reserveFiltersSubject.asObservable()
    }

    private val reserveEnabledSubject : BehaviorSubject<Boolean> = BehaviorSubject.create()
    fun getReserveEnabledStream() : Observable<Boolean>{
        return reserveEnabledSubject.asObservable()
    }

    fun updated(entries: List<DisplayableReserveFilter>, position: Int, selectedValue: Int){
        try {
            entries[position].setSelected(selectedValue)
            reserveFiltersSubject.onNext(entries)
            for (entry in entries) {
                if(!entry.isSelected()){
                    reserveEnabledSubject.onNext(false)
                    return
                }
            }
            reserveEnabledSubject.onNext(true)
        }catch (e: IndexOutOfBoundsException){

        }

    }

}

class DisplayableReserveFilter(filter: ReserveFilter) : ReserveFilter() {
    private val DEFAULT_VALUE = -1
    private var selectedValue : Int = DEFAULT_VALUE

    init {
        this.name = filter.name
        this.values = filter.values
    }

    fun isSelected(): Boolean {
        return selectedValue != DEFAULT_VALUE
    }

    fun setSelected(value: Int){
        if (value>0 && value<values.count()){
            when (value){
                selectedValue -> selectedValue = DEFAULT_VALUE // deselect if was already selected
                else -> selectedValue = value // update value otherwise
            }
        }else{
            throw IndexOutOfBoundsException()
        }
    }

}
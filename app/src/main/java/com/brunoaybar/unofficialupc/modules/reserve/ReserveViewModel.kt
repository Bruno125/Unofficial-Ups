package com.brunoaybar.unofficialupc.modules.reserve

import com.brunoaybar.unofficialupc.R
import com.brunoaybar.unofficialupc.UpcApplication
import com.brunoaybar.unofficialupc.data.models.ReserveFilter
import com.brunoaybar.unofficialupc.data.models.ReserveOption
import com.brunoaybar.unofficialupc.data.repository.UniversityInfoRepository
import com.brunoaybar.unofficialupc.data.source.remote.responses.ReserveAvailabilityResponse
import com.brunoaybar.unofficialupc.utils.Utils
import com.brunoaybar.unofficialupc.utils.interfaces.StringProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rx.Observable
import rx.subjects.BehaviorSubject
import javax.inject.Inject

class ReserveViewModel {

    @Inject
    lateinit var repository: UniversityInfoRepository

    @Inject
    lateinit var stringProvider: StringProvider

    init {
        UpcApplication.getViewModelsComponent().inject(this)
    }

    fun load(){
        var defaultError = stringProvider.getString(R.string.error_fill_fields)
        repository.reserveFilters.flatMap { Observable.from(it) }
                .filter { !it.values.isEmpty() }.toList()
                .subscribe ({ info ->
                    when (info.isEmpty()){
                        false -> {
                            val defaultHint = stringProvider.getString(R.string.text_reserve_filter_hint);
                            val result = info.map { DisplayableReserveFilter(it,defaultHint) }
                            reserveFiltersSubject.onNext(result)
                        }
                        true -> reserveFiltersSubject.onError(Utils.getError(defaultError))
                    }
                }, { reserveFiltersSubject.onError(Utils.getError(it,defaultError)) })
    }

    private val reserveFiltersSubject : BehaviorSubject<List<DisplayableReserveFilter>> = BehaviorSubject.create()
    fun getReserveEntries() : Observable<List<DisplayableReserveFilter>>{
        return reserveFiltersSubject.asObservable()
    }

    private val reserveEnabledSubject : BehaviorSubject<Boolean> = BehaviorSubject.create()
    fun getReserveEnabledStream() : Observable<Boolean>{
        return reserveEnabledSubject.asObservable()
    }

    fun updatedEntry(entries: List<DisplayableReserveFilter>, position: Int){
        try{
            val newEntries = entries.map{ it.copy() }
            newEntries[position].expanded = !newEntries[position].expanded
            reserveFiltersSubject.onNext(newEntries)
        }catch (e: IndexOutOfBoundsException){

        }
    }

    fun selectedFilterValue(entries: List<DisplayableReserveFilter>, position: Int, selectedValue: Int){
        try {
            val newEntries = entries.map{ it.copy() }
            newEntries[position].setSelectedValue(selectedValue)
            newEntries[position].expanded = false
            reserveFiltersSubject.onNext(newEntries)
            for (entry in newEntries) {
                if(!entry.isSelected()){
                    reserveEnabledSubject.onNext(false)
                    return
                }
            }
            reserveEnabledSubject.onNext(true)
        }catch (e: IndexOutOfBoundsException){

        }
    }


    fun applyFilters(filters: List<DisplayableReserveFilter>): Observable<String>{
        return repository.getReserveOptions(filters).map { Gson().toJson(it) }
    }

    fun parseReserveOptionsData(data: String): List<ReserveOption>{
        return Gson().fromJson<Array<ReserveOption>>(data,Array<ReserveOption>::class.java).toList()
    }


}

class DisplayableReserveFilter(val filter: ReserveFilter, val defaultHint: String) : ReserveFilter() {
    private val DEFAULT_VALUE = -1
    private var selectedValue : Int = DEFAULT_VALUE
    public var expanded = false

    init {
        this.name = filter.name
        this.values = filter.values
        this.key = filter.key
        this.serviceKey = filter.serviceKey
        this.isCustom = filter.isCustom
    }

    fun isSelected(): Boolean {
        return selectedValue != DEFAULT_VALUE
    }

    fun getSelectedValue(): Int{
        return selectedValue
    }


    fun setSelectedValue(value: Int){
        val isInRange = value>=0 && value<values.count()
        if (isInRange){
            when (value){
                selectedValue -> selectedValue = DEFAULT_VALUE // deselect if was already selected
                else -> selectedValue = value // update value otherwise
            }
            selected = value
        }else{
            throw IndexOutOfBoundsException()
        }
    }

    fun getHint(): String {
        when(isSelected()){
            true -> return values[selectedValue].value
            false -> return defaultHint
        }
    }

    fun copy(): DisplayableReserveFilter{
        val copy = DisplayableReserveFilter(filter,defaultHint)
        copy.expanded = expanded
        copy.selectedValue = selectedValue
        return copy
    }

}
package com.brunoaybar.unofficialupc.modules.reserve

import com.brunoaybar.unofficialupc.R
import com.brunoaybar.unofficialupc.UpcApplication
import com.brunoaybar.unofficialupc.data.models.ReserveFilter
import com.brunoaybar.unofficialupc.data.models.ReserveOption
import com.brunoaybar.unofficialupc.data.repository.UniversityInfoRepository
import com.brunoaybar.unofficialupc.utils.Utils
import com.brunoaybar.unofficialupc.utils.interfaces.StringProvider
import com.google.gson.Gson
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

    private val reserveOptionsSubject : BehaviorSubject<List<DisplayableReserveOption>> = BehaviorSubject.create()
    fun getReserveOptions(): Observable<List<DisplayableReserveOption>>{
        return reserveOptionsSubject.asObservable()
    }


    fun loadOptions(data: String){
        val options = Gson().fromJson<Array<ReserveOption>>(data,Array<ReserveOption>::class.java).toList()
        reserveOptionsSubject.onNext(options.map{ DisplayableReserveOption(false,it) })
    }

    fun selectedReserveOption(options: List<DisplayableReserveOption>, selected: Int){

        if(options[selected].isSelected){
            options[selected].isSelected = false
        }else{
            options.filter { it.isSelected }.map { it.isSelected = false }
            options[selected].isSelected = true
        }

        reserveOptionsSubject.onNext(options)
        reserveEnabledSubject.onNext(!options.filter { it.isSelected }.isEmpty())
    }

    fun requestedReserve(options: List<DisplayableReserveOption>): Observable<String>{
        val selectedOption = options.filter { it.isSelected }.firstOrNull()
        if (selectedOption != null) {
            return repository.reserve(selectedOption)
        } else{
            val msg = stringProvider.getString(R.string.text_reserve_pick_option)
            return Observable.just(msg)
        }
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
        this.selected = filter.selected
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
                selectedValue -> { // deselect if was already selected
                    selectedValue = DEFAULT_VALUE
                    selected = DEFAULT_VALUE
                }
                else -> { // update value otherwise
                    selectedValue = value
                    selected = value
                }
            }

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
        copy.selected = selected
        return copy
    }

}

class DisplayableReserveOption(var isSelected: Boolean, option: ReserveOption) :
        ReserveOption(option.code, option.name, option.venue, option.datetime, option.duration)
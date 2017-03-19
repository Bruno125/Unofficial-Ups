package com.brunoaybar.unofficialupc.modules.reserve

import com.brunoaybar.unofficialupc.R
import com.brunoaybar.unofficialupc.UpcApplication
import com.brunoaybar.unofficialupc.data.models.ReserveFilter
import com.brunoaybar.unofficialupc.data.repository.UniversityInfoRepository
import com.brunoaybar.unofficialupc.utils.interfaces.StringProvider
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
        repository.reserveFilters.flatMap { Observable.from(it) }
                .filter { !it.values.isEmpty() }.toList()
                .subscribe ({ info ->
                    when (info.isEmpty()){
                        false -> {
                            val defaultHint = stringProvider.getString(R.string.text_reserve_filter_hint);
                            val result = info.map { DisplayableReserveFilter(it,defaultHint) }
                            reserveFiltersSubject.onNext(result)
                        }
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

    fun updatedEntry(entries: List<DisplayableReserveFilter>, position: Int){
        try{
            entries[position].expanded = !entries[position].expanded
            reserveFiltersSubject.onNext(entries)
        }catch (e: IndexOutOfBoundsException){

        }
    }

    fun selectedFilterValue(entries: List<DisplayableReserveFilter>, position: Int, selectedValue: Int){
        try {
            entries[position].setSelected(selectedValue)
            entries[position].expanded = false
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

class DisplayableReserveFilter(filter: ReserveFilter, val defaultHint: String) : ReserveFilter() {
    private val DEFAULT_VALUE = -1
    private var selectedValue : Int = DEFAULT_VALUE
    public var expanded = false

    init {
        this.name = filter.name
        this.values = filter.values
    }

    fun isSelected(): Boolean {
        return selectedValue != DEFAULT_VALUE
    }

    fun getSelectedValue(): Int{
        return selectedValue
    }

    fun setSelected(value: Int){
        val isInRange = value>=0 && value<values.count()
        if (isInRange){
            when (value){
                selectedValue -> selectedValue = DEFAULT_VALUE // deselect if was already selected
                else -> selectedValue = value // update value otherwise
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
}
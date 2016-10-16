package com.brunoaybar.unofficalupc.modules.attendance;

import android.support.annotation.NonNull;

import com.brunoaybar.unofficalupc.data.models.Absence;
import com.brunoaybar.unofficalupc.data.source.UpcRepository;

import rx.Observable;

/**
 * Created by brunoaybar on 16/10/2016.
 */

public class AbsencesViewModel {

    @NonNull
    private UpcRepository mRepository;

    public AbsencesViewModel(@NonNull UpcRepository repository){
        mRepository = repository;
    }

    public Observable<Absence> getAttendanceStream(){

        return Observable.create(subscriber ->
                //Get user session
                mRepository.getSession().subscribe(session -> {
                    //Use session to get absences
                    mRepository.getAbsences(session)
                    .flatMap(Observable::from)
                    .subscribe(subscriber::onNext,subscriber::onError);
        }));

    }

}

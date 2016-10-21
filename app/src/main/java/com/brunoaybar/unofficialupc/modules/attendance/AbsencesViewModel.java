package com.brunoaybar.unofficialupc.modules.attendance;

import android.support.annotation.NonNull;

import com.brunoaybar.unofficialupc.data.models.Absence;
import com.brunoaybar.unofficialupc.data.source.UpcRepository;

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

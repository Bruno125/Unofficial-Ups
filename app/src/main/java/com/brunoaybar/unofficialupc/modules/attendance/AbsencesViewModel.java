package com.brunoaybar.unofficialupc.modules.attendance;

import android.support.annotation.NonNull;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.models.Absence;
import com.brunoaybar.unofficialupc.data.source.UpcRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by brunoaybar on 16/10/2016.
 */

public class AbsencesViewModel {

    @Inject
    UpcRepository mRepository;

    public AbsencesViewModel(){
        UpcApplication.getComponent().inject(this);
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

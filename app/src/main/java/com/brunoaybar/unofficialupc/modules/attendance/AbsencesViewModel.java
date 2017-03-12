package com.brunoaybar.unofficialupc.modules.attendance;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.models.Absence;
import com.brunoaybar.unofficialupc.data.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by brunoaybar on 16/10/2016.
 */

public class AbsencesViewModel {

    @Inject
    UserRepository mRepository;

    public AbsencesViewModel(){
        UpcApplication.getViewModelsComponent().inject(this);
    }

    public Observable<Absence> getAttendanceStream(){
        return mRepository.getAbsences().flatMap(Observable::from);
    }

}

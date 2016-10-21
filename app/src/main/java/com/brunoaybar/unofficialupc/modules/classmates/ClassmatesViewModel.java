package com.brunoaybar.unofficialupc.modules.classmates;

import com.brunoaybar.unofficialupc.data.models.Classmate;
import com.brunoaybar.unofficialupc.data.source.UpcRepository;

import java.util.List;

import rx.Observable;

/**
 * Created by brunoaybar on 17/10/2016.
 */

public class ClassmatesViewModel {

    private UpcRepository mRepository;

    public ClassmatesViewModel(UpcRepository repository) {
        mRepository = repository;
    }

    public Observable<List<Classmate>> getClassmates(String courseCode){
        return Observable.create(subscriber ->
                //Get current session
                mRepository.getSession().subscribe(user -> {
                    //Use current session to get menu_course
                    mRepository.getClassmates(user,courseCode)
                            .subscribe(subscriber::onNext,subscriber::onError);
                }));
    }


}

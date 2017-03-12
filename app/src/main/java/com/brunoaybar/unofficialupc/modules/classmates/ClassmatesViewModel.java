package com.brunoaybar.unofficialupc.modules.classmates;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.models.Classmate;
import com.brunoaybar.unofficialupc.data.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by brunoaybar on 17/10/2016.
 */

public class ClassmatesViewModel {

    @Inject
    UserRepository mRepository;

    public ClassmatesViewModel() {
        UpcApplication.getViewModelsComponent().inject(this);
    }

    public Observable<List<Classmate>> getClassmates(String courseCode){
        return mRepository.getClassmates(courseCode);
    }

}

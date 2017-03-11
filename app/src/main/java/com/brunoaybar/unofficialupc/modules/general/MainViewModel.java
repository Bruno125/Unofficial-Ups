package com.brunoaybar.unofficialupc.modules.general;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.data.repository.SessionRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * View Model for the MainActivity
 */

public class MainViewModel {

    @Inject
    SessionRepository mRepository;

    public MainViewModel(){
        UpcApplication.getViewModelsComponent().inject(this);
    }

    private BehaviorSubject<Boolean> mLogoutStream = BehaviorSubject.create();
    public Observable<Boolean> getLogoutStream(){
        return mLogoutStream.asObservable();
    }

    public void performLogout(){
        mRepository.logout().subscribe(result ->{
            mLogoutStream.onNext(result);
        },mLogoutStream::onError);
    }

}

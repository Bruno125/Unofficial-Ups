package com.brunoaybar.unofficialupc.modules.general;

import android.support.annotation.NonNull;

import com.brunoaybar.unofficialupc.data.source.UpcRepository;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * View Model for the MainActivity
 */

public class MainViewModel {

    @NonNull
    private UpcRepository mRepository;

    public MainViewModel(UpcRepository repository){
        mRepository = repository;
    }

    private BehaviorSubject<Boolean> mLogoutStream = BehaviorSubject.create();
    public Observable<Boolean> getLogoutStream(){
        return mLogoutStream.asObservable();
    }

    public void performLogout(){
        mRepository.logout().subscribe(result ->{
            mLogoutStream.onNext(result);
        },error -> {});
    }

}

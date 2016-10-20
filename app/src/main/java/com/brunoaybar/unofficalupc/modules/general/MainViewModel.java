package com.brunoaybar.unofficalupc.modules.general;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.brunoaybar.unofficalupc.data.source.UpcRepository;

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

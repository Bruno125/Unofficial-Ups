package com.brunoaybar.unofficalupc.modules.timetable;

import android.support.annotation.NonNull;

import com.brunoaybar.unofficalupc.data.models.Timetable;
import com.brunoaybar.unofficalupc.data.models.User;
import com.brunoaybar.unofficalupc.data.source.UpcRepository;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * View Model for the TimetableFragment
 */

public class TimetableViewModel {

    @NonNull
    private UpcRepository mRepository;

    public TimetableViewModel(@NonNull UpcRepository repository){
        mRepository = repository;
    }

    public Observable<Timetable> getTimetable(){
        return Observable.create(new Observable.OnSubscribe<Timetable>() {
            @Override
            public void call(Subscriber<? super Timetable> subscriber) {
                Observable.zip( //Get session info
                        mRepository.getToken().subscribeOn(Schedulers.immediate()),
                        mRepository.getUserCode().subscribeOn(Schedulers.immediate()),
                        (token,userCode) -> new User(token,userCode,null))
                        .subscribeOn(Schedulers.io())
                        .subscribe(user -> { //use current session to get timetable
                            mRepository.getTimeTable(user.getUserCode(),user.getToken())
                                    .subscribe( response ->{
                                        subscriber.onNext(response);
                                        subscriber.onCompleted();
                                    },subscriber::onError);
                        });
            }
        });
    }

}

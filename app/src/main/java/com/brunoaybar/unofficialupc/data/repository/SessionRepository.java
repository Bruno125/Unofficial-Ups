package com.brunoaybar.unofficialupc.data.repository;

import com.brunoaybar.unofficialupc.data.models.User;

import rx.Observable;

/**
 * Created by brunoaybar on 11/03/2017.
 */

public interface SessionRepository {
    Observable<User> getSession();
    Observable<User> saveSession(User user);
    Observable<Boolean> logout();
}

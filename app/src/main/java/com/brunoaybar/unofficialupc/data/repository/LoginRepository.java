package com.brunoaybar.unofficialupc.data.repository;

import rx.Observable;

/**
 * Created by brunoaybar on 11/03/2017.
 */

public interface LoginRepository {
    Observable<Boolean> verifyUserSession();
    Observable<Boolean> login(String userCode, String password, boolean rememberCredentials);
}

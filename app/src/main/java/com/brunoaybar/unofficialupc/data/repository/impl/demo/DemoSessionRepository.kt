package com.brunoaybar.unofficialupc.data.repository.impl.demo

import android.content.Context
import com.brunoaybar.unofficialupc.data.models.User
import com.brunoaybar.unofficialupc.data.repository.SessionRepository
import com.brunoaybar.unofficialupc.data.source.injection.DataComponent
import com.brunoaybar.unofficialupc.data.source.interfaces.ApplicationDao
import com.brunoaybar.unofficialupc.data.source.preferences.PreferenceUtils
import rx.Observable
import rx.schedulers.Schedulers
import javax.inject.Inject

class DemoSessionRepository(dataComponent: DataComponent) : SessionRepository{
    init { dataComponent.inject(this) }

    @Inject lateinit var context: Context
    private val key_logged = "demo_is_logged"

    override fun getSession(): Observable<User> {
        val isLogged = PreferenceUtils.getBool(context,key_logged)
        when(isLogged){
            true -> return Observable.just(User("123","demo","123456"))
            false -> return Observable.just(User())
        }
    }

    override fun saveSession(user: User?): Observable<User> {
        PreferenceUtils.saveBoolean(context,key_logged,true)
        return Observable.just(user)
    }

    override fun logout(): Observable<Boolean> {
        PreferenceUtils.saveBoolean(context,key_logged,false)
        return Observable.just(true)
    }

}

package com.brunoaybar.unofficialupc.data.repository.impl.demo

import com.brunoaybar.unofficialupc.data.models.User
import com.brunoaybar.unofficialupc.data.repository.LoginRepository
import com.brunoaybar.unofficialupc.data.repository.SessionRepository
import com.brunoaybar.unofficialupc.data.source.injection.BaseDataComponent
import com.brunoaybar.unofficialupc.data.source.remote.responses.BaseResponse
import com.brunoaybar.unofficialupc.data.source.remote.responses.ServiceException
import rx.Observable
import javax.inject.Inject

class DemoLoginRepository(dataComponent: BaseDataComponent) : LoginRepository {
    init { dataComponent.inject(this) }

    @Inject lateinit var sessionRepo: SessionRepository

    override fun verifyUserSession(): Observable<Boolean> {
        return sessionRepo.session.map(User::hasValidSession)
    }

    override fun login(userCode: String?, password: String?, rememberCredentials: Boolean): Observable<Boolean> {
        val isDemo = userCode == "demo" && password == "123456"
        when(isDemo){
            true -> {
                return Observable.just(isDemo)
            }
            false -> return Observable.error(getServiceException("Credenciales invalidas"))
        }
    }

    private fun getServiceException(msg: String): ServiceException{
        val fakeResponse = BaseResponse()
        fakeResponse.errorMessage = msg
        return ServiceException(fakeResponse)
    }

}
package com.brunoaybar.unofficialupc.modules.auth

import com.brunoaybar.unofficialupc.data.models.User
import com.brunoaybar.unofficialupc.data.source.preferences.UserPreferencesDataSource
import com.brunoaybar.unofficialupc.data.source.remote.UpcServiceDataSource
import com.brunoaybar.unofficialupc.data.source.remote.responses.ServiceException
import com.brunoaybar.unofficialupc.testUtils.assertObservable
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import rx.Observable
import rx.Observable.just

class LoginDataModelTest{

    private val USER_CODE = "u201313295"
    private val USER_PASS = "Testpass1234"
    private val USER_TOKEN = "a34sadfa32fads"

    private lateinit var dataModel : LoginRepository2

    @Test
    fun shouldFail_When_SaveSessionNotEnabled(){
        dataModel = LoginRepository2(
                createMockPreferences(false, null, null, null),
                createMockService())
        assertObservable(dataModel.verifyUserSession(),false)
    }

    @Test
    fun shouldFail_When_NoCredentials_And_NoValidSession(){
        dataModel = LoginRepository2(
                createMockPreferences(true, null, null, null),
                createMockService()
        )
        assertObservable(dataModel.verifyUserSession(),false)
    }

    @Test
    fun shouldFailWhen_CredentialsAreNoLongerValid(){
        dataModel = LoginRepository2(createMockPreferences(true, null, USER_CODE, USER_PASS),
                mock<UpcServiceDataSource> {
                    on { login(any(), any()) } doReturn Observable.error(ServiceException())
                }
        )
        assertObservable(dataModel.verifyUserSession(),false)
    }

    @Test
    fun shouldFailWhen_TokenNoLongerValid(){
        dataModel = LoginRepository2(
                createMockPreferences(true, USER_TOKEN, USER_CODE, null),
                mock<UpcServiceDataSource> {
                    on { validateToken(USER_CODE, USER_TOKEN) } doReturn just(false)
                }
        )
        assertObservable(dataModel.verifyUserSession(),false)
    }

    @Test
    fun shouldVerifyWhen_CredentialsAreValid(){
        val user = User(USER_TOKEN,USER_CODE,USER_PASS)
        dataModel = LoginRepository2(
                createMockPreferences(true, USER_TOKEN, USER_CODE, USER_PASS),
                mock<UpcServiceDataSource> {
                    on { login(USER_CODE, USER_PASS) } doReturn just(user)
                }
        )
        assertObservable(dataModel.verifyUserSession(),true)
    }

    @Test
    fun shouldVerifyWhen_TokenStillValid(){
        dataModel = LoginRepository2(
                createMockPreferences(true, USER_TOKEN, USER_CODE, null),
                mock<UpcServiceDataSource> {
                    on { validateToken(USER_CODE, USER_TOKEN) } doReturn just(true)
                }
        )
        assertObservable(dataModel.verifyUserSession(),true)
    }

    fun createMockService() : UpcServiceDataSource{
        return mock<UpcServiceDataSource>{
            on { login(any(),any()) } doReturn Observable.just(User())
            on { validateToken(any(),any()) } doReturn Observable.just(false)
        }
    }

    fun createMockPreferences(agreesToSaveSession: Boolean, token: String?, userCode: String?, pass: String?) : UserPreferencesDataSource{
        return mock<UserPreferencesDataSource>{
            on { userAgreeToSaveSession() } doReturn just(agreesToSaveSession)
            on { getToken() } doReturn just(token)
            on { getUserCode() } doReturn just(userCode)
            on { getSavedPassword() } doReturn just(pass)
            on { saveUser(any())} doReturn User(token,userCode,pass)
        }
    }

}
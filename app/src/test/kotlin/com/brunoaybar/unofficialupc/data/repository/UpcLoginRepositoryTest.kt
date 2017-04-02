package com.brunoaybar.unofficialupc.data.repository

import com.brunoaybar.unofficialupc.AndroidTest
import com.brunoaybar.unofficialupc.data.models.User
import com.brunoaybar.unofficialupc.data.repository.impl.upc.UpcLoginRepository
import com.brunoaybar.unofficialupc.data.source.interfaces.ApplicationDao
import com.brunoaybar.unofficialupc.data.source.interfaces.RemoteSource
import com.brunoaybar.unofficialupc.data.source.preferences.UserPreferencesDataSource
import com.brunoaybar.unofficialupc.data.source.remote.UpcServiceDataSource
import com.brunoaybar.unofficialupc.testUtils.assertObservable
import com.brunoaybar.unofficialupc.testUtils.getDataComponent
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Observable
import javax.inject.Inject

class UpcLoginRepositoryTest{

    private val USER_CODE = "u201313295"
    private val USER_PASS = "Testpass1234"
    private val USER_TOKEN = "a34sadfa32fads"

    @Inject
    lateinit var mockAppDao: ApplicationDao
    @Inject
    lateinit var mockRemoteSource: RemoteSource
    @Inject
    lateinit var mockSessionRepo: SessionRepository

    private lateinit var repository: UpcLoginRepository

    @Before
    fun setUp(){
        val dataComponent = getDataComponent()
        repository = UpcLoginRepository(dataComponent)
        dataComponent.inject(this)
    }

    @Test
    fun shouldFail_When_Couldnt_Verify_Session(){
        val userMock = mock<User>{ on { hasValidSession() } doReturn false }
        whenever(mockSessionRepo.session).then { Observable.just(userMock) }

        /*dataModel = LoginRepository(
                createMockPreferences(false, null, null, null),
                createMockService())*/
        assertObservable(repository.verifyUserSession(),false)
    }
    /*
    @Test
    fun shouldFail_When_NoCredentials_And_NoValidSession(){
        /*dataModel = LoginRepository2(
                createMockPreferences(true, null, null, null),
                createMockService()
        )*/
        assertObservable(repository.verifyUserSession(),false)
    }

    @Test
    fun shouldFailWhen_CredentialsAreNoLongerValid(){
        /*dataModel = LoginRepository2(createMockPreferences(true, null, USER_CODE, USER_PASS),
                mock<UpcServiceDataSource> {
                    on { login(any(), any()) } doReturn Observable.error(ServiceException())
                }
        )*/
        assertObservable(repository.verifyUserSession(),false)
    }

    @Test
    fun shouldFailWhen_TokenNoLongerValid(){
        /*dataModel = LoginRepository2(
                createMockPreferences(true, USER_TOKEN, USER_CODE, null),
                mock<UpcServiceDataSource> {
                    on { validateToken(USER_CODE, USER_TOKEN) } doReturn just(false)
                }
        )*/
        assertObservable(repository.verifyUserSession(),false)
    }

    @Test
    fun shouldVerifyWhen_CredentialsAreValid(){
        /*val user = User(USER_TOKEN,USER_CODE,USER_PASS)
        dataModel = LoginRepository2(
                createMockPreferences(true, USER_TOKEN, USER_CODE, USER_PASS),
                mock<UpcServiceDataSource> {
                    on { login(USER_CODE, USER_PASS) } doReturn just(user)
                }
        )*/
        assertObservable(repository.verifyUserSession(),true)
    }

    @Test
    fun shouldVerifyWhen_TokenStillValid(){
        /*dataModel = LoginRepository2(
                createMockPreferences(true, USER_TOKEN, USER_CODE, null),
                mock<UpcServiceDataSource> {
                    on { validateToken(USER_CODE, USER_TOKEN) } doReturn just(true)
                }
        )*/
        assertObservable(repository.verifyUserSession(),true)
    }*/

    fun createMockService() : UpcServiceDataSource {
        return mock<UpcServiceDataSource>{
            on { login(any(), any()) } doReturn Observable.just(User())
            on { validateToken(any(), any()) } doReturn Observable.just(false)
        }
    }

    fun createMockPreferences(agreesToSaveSession: Boolean, token: String?, userCode: String?, pass: String?) : UserPreferencesDataSource {
        return mock<UserPreferencesDataSource>{
            on { userAgreeToSaveSession() } doReturn Observable.just(agreesToSaveSession)
            on { getToken() } doReturn Observable.just(token)
            on { getUserCode() } doReturn Observable.just(userCode)
            on { getSavedPassword() } doReturn Observable.just(pass)
            on { saveUser(any())} doReturn User(token,userCode,pass)
        }
    }


}
package com.brunoaybar.unofficialupc.data.source.preferences

import com.brunoaybar.unofficialupc.AndroidTest
import com.brunoaybar.unofficialupc.data.models.User
import com.brunoaybar.unofficialupc.testUtils.assertObservable
import com.brunoaybar.unofficialupc.utils.interfaces.DateProvider
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class UserPreferencesDataSourceTest : AndroidTest() {

    private lateinit var source : UserPreferencesDataSource
    private lateinit var user : User

    private val USER_CODE = "U201313295"
    private val USER_PASS = "TestPass1234"
    private val USER_TOKEN = "109fad235tf23zf"
    private val TEST_DATE = SimpleDateFormat("yyyyMMdd",Locale.US).parse("20170202")
    private val TEST_DATE_NEVER = Date(0)

    @Before
    fun setUp(){
        source = UserPreferencesDataSource(context(),getMockedDateProvider())
        user = createDummyUser()
    }

    @Test
    fun shouldSaveAgreeToSaveSession(){
        assertSessionSaved(true)
        assertSessionSaved(false)
        assertSessionSaved(true)
    }

    private fun assertSessionSaved(agrees: Boolean){
        source.setUserAgreeToSaveSession(agrees)
        assertObservable(source.userAgreeToSaveSession(),agrees)
    }

    @Test
    fun shouldSaveUser(){
        source.saveUser(user)

        assertObservable(source.token,USER_TOKEN)
        assertObservable(source.savedPassword,USER_PASS)
        assertObservable(source.userCode,USER_CODE)
        assertObservable(source.lastUpdateTime,TEST_DATE)
    }

    @Test
    fun shouldRemoveUser(){
        source.saveUser(user)
        source.removeUser()

        assertObservable(source.token,null)
        assertObservable(source.savedPassword,null)
        assertObservable(source.userCode,null)
        assertObservable(source.lastUpdateTime,TEST_DATE)
    }

    @Test
    fun shouldNotHaveSavedTimestamp(){
        assertObservable(source.lastUpdateTime,TEST_DATE_NEVER)
    }

    fun createDummyUser() : User{
        return User(USER_TOKEN,USER_CODE,USER_PASS)
    }

    fun getMockedDateProvider() : DateProvider {
        return mock<DateProvider> {
            on { now } doReturn TEST_DATE
            on { never } doReturn TEST_DATE_NEVER
        }
    }

}

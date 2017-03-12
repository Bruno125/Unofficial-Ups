package com.brunoaybar.unofficialupc.data.models

import org.junit.Test
import kotlin.test.assertFalse

class UserTest{

    private val TEST_TOKEN = "test"
    private val TEST_CODE = "U201313295"
    private val TEST_PASS = "test"

    @Test
    fun invalidSessionWhen_NoToken_NoCode(){
        val user = User(null,null,null)
        assertFalse { user.hasValidSession() }
    }
    @Test
    fun invalidSessionWhen_HasToken_NoCode(){
        val user = User(TEST_TOKEN,null,null)
        assertFalse { user.hasValidSession() }
    }
    @Test
    fun invalidSessionWhen_HasCode_NoToken(){
        val user = User(null,TEST_CODE,null)
        assertFalse { user.hasValidSession() }
    }

    @Test
    fun validSessionWhen_HasToken_HasCode(){
        val user = User(TEST_TOKEN,TEST_CODE,null)
        assert(user.hasValidSession())
    }


    @Test
    fun invalidCredentialsWhen_NoCode_NoPass(){
        val user = User(null,null,null)
        assertFalse { user.hasValidCredentials() }
    }
    @Test
    fun invalidCredentialsWhen_HasCode_NoPass(){
        val user = User(null,TEST_CODE,null)
        assertFalse { user.hasValidCredentials() }
    }
    @Test
    fun invalidCredentialsWhen_NoCode_HasPass(){
        val user = User(null,null,TEST_PASS)
        assertFalse { user.hasValidCredentials() }
    }
    @Test
    fun validCredentialsWhen_HasCode_HasPass(){
        val user = User(null,TEST_CODE,TEST_PASS)
        assert(user.hasValidCredentials())
    }

}


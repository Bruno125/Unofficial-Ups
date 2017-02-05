package com.brunoaybar.unofficialupc.data.models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by brunoaybar on 10/01/2017.
 */

public class UserTest {
    @Test
    public void hasInvalidSession_NoToken_NoCode() throws Exception {
        User user = new User(null,null,null);
        assertEquals(false,user.hasValidSession());
    }
    @Test
    public void hasInvalidSession_NoToken() throws Exception {
        User user = new User(null,"test",null);
        assertEquals(false,user.hasValidSession());
    }

    @Test
    public void hasInvalidSession_NoCode() throws Exception {
        User user = new User("test",null,null);
        assertEquals(false,user.hasValidSession());
    }
    @Test
    public void hasInvalidSession_EmptyToken_EmptyCode() throws Exception {
        User user = new User("","",null);
        assertEquals(false,user.hasValidSession());
    }
    @Test
    public void hasInvalidSession_EmptyToken() throws Exception {
        User user = new User("","test",null);
        assertEquals(false,user.hasValidSession());
    }

    @Test
    public void hasInvalidSession_EmptyCode() throws Exception {
        User user = new User("test","",null);
        assertEquals(false,user.hasValidSession());
    }

    @Test
    public void hasValidSession() throws Exception {
        User user = new User("test","test",null);
        assertEquals(true,user.hasValidSession());
    }


    @Test
    public void hasInvalidCredentials_NoCode_NoPass() throws Exception {
        User user = new User(null,null,null);
        assertEquals(false,user.hasValidCredentials());
    }
    @Test
    public void hasInvalidCredentials_NoCode() throws Exception {
        User user = new User(null,null,"test");
        assertEquals(false,user.hasValidCredentials());
    }
    @Test
    public void hasInvalidCredentials_NoPass() throws Exception {
        User user = new User(null,"test",null);
        assertEquals(false,user.hasValidCredentials());
    }
    @Test
    public void hasInvalidCredentials_EmptyCode_EmptyPass() throws Exception {
        User user = new User("","","");
        assertEquals(false,user.hasValidCredentials());
    }
    @Test
    public void hasInvalidCredentials_EmptyCode() throws Exception {
        User user = new User("","","test");
        assertEquals(false,user.hasValidCredentials());
    }
    @Test
    public void hasInvalidCredentials_EmptyPass() throws Exception {
        User user = new User("","test","");
        assertEquals(false,user.hasValidCredentials());
    }
    @Test
    public void hasValidCrendital() throws Exception {
        User user = new User(null,"user","password");
        assertEquals(true,user.hasValidCredentials());
    }

}
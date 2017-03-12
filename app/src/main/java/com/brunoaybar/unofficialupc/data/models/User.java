package com.brunoaybar.unofficialupc.data.models;

import android.text.TextUtils;

import com.brunoaybar.unofficialupc.utils.Utils;

/**
 * Model class for a User
 */

public class User {

    private String userCode;
    private String names;
    private String lastnames;
    private String genre;
    private String token;
    private String currentSemester;
    private String savedPassword;

    public User(){}
    public User(String token, String userCode, String password){
        setToken(token);
        setUserCode(userCode);
        setSavedPassword(password);
    }

    public boolean hasValidSession(){
        return !Utils.isEmpty(token) && !Utils.isEmpty(userCode);
    }

    public boolean hasValidCredentials(){
        return !Utils.isEmpty(userCode) && !Utils.isEmpty(savedPassword);
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastnames() {
        return lastnames;
    }

    public void setLastnames(String lastnames) {
        this.lastnames = lastnames;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(String currentSemester) {
        this.currentSemester = currentSemester;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getSavedPassword() {
        return savedPassword;
    }

    public User setSavedPassword(String savedPassword) {
        this.savedPassword = savedPassword;
        return this;
    }
}

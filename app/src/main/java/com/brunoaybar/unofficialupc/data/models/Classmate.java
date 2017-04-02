package com.brunoaybar.unofficialupc.data.models;

/**
 * Created by brunoaybar on 17/10/2016.
 */

public class Classmate {

    private String name;
    private String code;
    private String photo;
    private String career;

    public Classmate() {
    }

    public Classmate(String name, String code, String photo, String career) {
        this.name = name;
        this.code = code;
        this.photo = photo;
        this.career = career;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }
}

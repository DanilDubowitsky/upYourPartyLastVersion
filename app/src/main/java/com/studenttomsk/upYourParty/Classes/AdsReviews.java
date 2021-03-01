package com.studenttomsk.upYourParty.Classes;

import com.studenttomsk.upYourParty.Classes.AuthPerson;

public class AdsReviews {

    private Long id;
    private String content;
    private Long adsid;
    private AuthPerson authPerson;
    private String rating;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAdsid() {
        return adsid;
    }

    public void setAdsid(Long adsid) {
        this.adsid = adsid;
    }

    public AuthPerson getAuthPerson() {
        return authPerson;
    }

    public void setAuthPerson(AuthPerson authPerson) {
        this.authPerson = authPerson;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
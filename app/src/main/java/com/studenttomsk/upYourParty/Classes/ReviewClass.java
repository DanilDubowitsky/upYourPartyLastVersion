package com.studenttomsk.upYourParty.Classes;

public class ReviewClass {
    String content;
    Long adsid;
    String rating;
    String date;

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


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ReviewClass(String content, Long adsid, String rating, String date) {
        this.content = content;
        this.adsid = adsid;
        this.rating = rating;
        this.date = date;
    }




}

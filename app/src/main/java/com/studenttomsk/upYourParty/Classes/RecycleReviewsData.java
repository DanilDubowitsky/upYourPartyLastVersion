package com.studenttomsk.upYourParty.Classes;

public class RecycleReviewsData {
    private int imageProfile;
    private String fio, review;
    private String raiting;


    public int getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(int imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRaiting() {
        return raiting;
    }

    public void setRaiting(String raiting) {
        this.raiting = raiting;
    }

    public RecycleReviewsData(int imageProfile, String fio, String review, String raiting) {
        this.imageProfile = imageProfile;
        this.fio = fio;
        this.review = review;
        this.raiting = raiting;
    }
}

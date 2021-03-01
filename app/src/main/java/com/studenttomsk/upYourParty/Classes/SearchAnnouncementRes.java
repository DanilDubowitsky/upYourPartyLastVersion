package com.studenttomsk.upYourParty.Classes;

import java.util.List;

public class SearchAnnouncementRes {
    private int id;
    private String title;
    private String price;
    private String city;
    private String category;
    private List<AdsImage> attachments;
    private Long myFavorite;
    private AdsProfile adsProfile;

    public Long getMyFavorite() {
        return myFavorite;
    }

    public void setMyFavorite(Long myFavorite) {
        this.myFavorite = myFavorite;
    }

    public AdsProfile getAdsProfile() {
        return adsProfile;
    }

    public void setAdsProfile(AdsProfile adsProfile) {
        this.adsProfile = adsProfile;
    }

    public List<AdsImage> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AdsImage> attachments) {
        this.attachments = attachments;
    }

    public long isMyFavorite() {
        return myFavorite;
    }

    public void setMyFavorite(long myFavorite) {
        this.myFavorite = myFavorite;
    }

    public List<AdsImage> getImagelist() {
        return attachments;
    }

    public void setImagelist(List<AdsImage> imagelist) {
        this.attachments = imagelist;
    }

    public SearchAnnouncementRes(int id, String title, String price, String city, String category, List<AdsImage> imagelist, AdsProfile adsProfile) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.city = city;
        this.category = category;
        this.attachments = imagelist;
        this.adsProfile = adsProfile;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

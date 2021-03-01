package com.studenttomsk.upYourParty.Classes;

import com.studenttomsk.upYourParty.Classes.AdsProfile;

import java.util.List;

public class AddAnnounceClass {
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

    public AdsProfile getAdsProfile() {
        return adsProfile;
    }

    public void setAdsProfile(AdsProfile adsProfile) {
        this.adsProfile = adsProfile;
    }

    public List<String> getAdsImages() {
        return adsImages;
    }

    public void setAdsImages(List<String> adsImages) {
        this.adsImages = adsImages;
    }

    public AddAnnounceClass(String title, String price, String city, String category, AdsProfile adsProfile, List<String> adsImages) {
        this.title = title;
        this.price = price;
        this.city = city;
        this.category = category;
        this.adsProfile = adsProfile;
        this.adsImages = adsImages;
    }

    private String title;
    private String price;
    private String city;
    private String category;
    private AdsProfile adsProfile;
    private List<String> adsImages;
}

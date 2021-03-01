package com.studenttomsk.upYourParty.Classes;

import com.studenttomsk.upYourParty.Classes.AdsProfile;

import java.util.List;

public class AnnouncementClass {
    private Long id;
   private String title;
   private String price;
   private String city;
    private String category;
    private AdsProfile adsProfile;
    private List<String> adsImages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getCategory() {
        return category;
    }

    public AnnouncementClass(long id, String title, String price, String city, String category, AdsProfile adsProfile, List<String> adsImages) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.city = city;
        this.category = category;
        this.adsProfile = adsProfile;
        this.adsImages = adsImages;
    }

    public List<String> getAdsImages() {
        return adsImages;
    }

    public void setAdsImages(List<String> adsImages) {
        this.adsImages = adsImages;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public AdsProfile getAdsProfile() {
        return adsProfile;
    }

    public void setAdsProfile(AdsProfile adsProfile) {
        this.adsProfile = adsProfile;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



}

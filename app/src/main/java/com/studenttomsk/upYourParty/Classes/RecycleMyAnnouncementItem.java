package com.studenttomsk.upYourParty.Classes;

public class RecycleMyAnnouncementItem {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String category;
    private String title;
    private String price;
    private AdsProfile adsProfile;

    public AdsProfile getAdsProfile() {
        return adsProfile;
    }

    public void setAdsProfile(AdsProfile adsProfile) {
        this.adsProfile = adsProfile;
    }

    public String getCategory() {
        return category;
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

    public RecycleMyAnnouncementItem(String category, String title, String price) {
        this.category = category;
        this.title = title;
        this.price = price;
    }
}

package com.studenttomsk.upYourParty.Classes;

public class AdsSearchFilterDto {
    private String title;
    private String sort;
    private String city;
    private String category;

    public AdsSearchFilterDto(String title, String price_one, String price_two, String city, String category, String sort) {
        this.title = title;
        this.sort = sort;
        this.city = city;
        this.category = category;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

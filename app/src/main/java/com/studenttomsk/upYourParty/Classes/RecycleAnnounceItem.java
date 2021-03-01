package com.studenttomsk.upYourParty.Classes;

public class RecycleAnnounceItem {
    private int imageResource;
    private String name;
    private String price;

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
    public int getImageResource(){
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price) {

        this.price = price;
    }

    public RecycleAnnounceItem(int imageResource, String name, String price) {
        this.imageResource = imageResource;
        this.name = name;
        this.price = price;
    }

}

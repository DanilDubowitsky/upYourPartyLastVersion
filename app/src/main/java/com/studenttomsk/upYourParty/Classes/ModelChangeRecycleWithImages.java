package com.studenttomsk.upYourParty.Classes;

public class ModelChangeRecycleWithImages {
    String imageUri;

    public String getImageUri() {
        return imageUri;
    }

    public void changeImage(String uri){
        this.imageUri=uri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public ModelChangeRecycleWithImages(String imageUri) {
        this.imageUri = imageUri;
    }
}

package com.studenttomsk.upYourParty.Classes;

import android.net.Uri;

public class ModelRecycleWithImages {
    private Uri image;

    public void changeImage(Uri uri){
        image = uri;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public ModelRecycleWithImages(Uri image) {
        this.image = image;
    }
}

package com.studenttomsk.upYourParty.Views.Fragments.AddAnnoun;

import com.studenttomsk.upYourParty.Classes.AddAnnounceClass;
import com.studenttomsk.upYourParty.Classes.AnnouncementClass;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public class PresenterAddAnnounce implements AddAnnounMethods.Presenter, AddAnnounMethods.Model.OnCompleteListener {
    AddAnnounMethods.View view;
    AddAnnounMethods.Model model;

    public PresenterAddAnnounce(AddAnnounMethods.View view, AddAnnounMethods.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void setPostAddAnnounce(AddAnnounceClass announce, String token) {
        model.addAnnounce(this, announce, token);
    }

    @Override
    public void setImagePost(MultipartBody.Part image) {
        model.postImage(this,image);
    }

    @Override
    public void deleteImagePost(List<String> name) {
        model.deleteImage(this,name);
    }

    @Override
    public void setPostChangeAnnounce(AnnouncementClass announce, String token) {
        model.changeAnnounce(this,announce,token);
    }

    @Override
    public void onFinish() {
        view.showMessage();
    }

    @Override
    public void onFinishDelete() {
        view.onSuccessDelete();
    }

    @Override
    public void onFinishChange() {
        view.onSuccessChange();
    }

    @Override
    public void onFinishUpload(Map<String,String> name) {
        view.successUploadImage(name);
    }

    @Override
    public void onFailure(String t) {
        view.onFailMessage(t);
    }


}

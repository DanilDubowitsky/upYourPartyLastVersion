package com.studenttomsk.upYourParty.Views.Fragments.Profile;

import com.studenttomsk.upYourParty.Classes.ChangeProfileClass;
import com.studenttomsk.upYourParty.Classes.PasswordClass;
import com.studenttomsk.upYourParty.Classes.ProfileNumbNameTel;

import okhttp3.MultipartBody;

public class PresenterProfile implements ProfileMethods.PresenterProfile, ProfileMethods.ModelProfile.onCompleteListener {
    ProfileMethods.ViewProfile view;
    ProfileMethods.ModelProfile model;

    public PresenterProfile(ProfileMethods.ViewProfile view, ProfileMethods.ModelProfile model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void setRequestProfileInfo(String token) {
        model.getProfileInfo(this,token);
    }

    @Override
    public void setConfEmail() {
        model.confEmail(this);
    }

    @Override
    public void setChangePost(ChangeProfileClass changeProfileClass, String token) {
        model.changeProfile(this,changeProfileClass,token);
    }

    @Override
    public void loadImage(MultipartBody.Part img) {
        model.loadImageProfile(this,img);
    }

    @Override
    public void setChangePasswordRequest(PasswordClass passwordClass, String token) {
        model.changePassword(this,passwordClass,token);
    }

    @Override
    public void onSuccessGetInfo(ProfileNumbNameTel info) {
        view.onSuccessLoadInfo(info);
    }

    @Override
    public void onSuccess(String s) {
        view.showSuccessMessage(s);
    }

    @Override
    public void onFinishedLoadImage(String t) {
        view.successUploadImage(t);
    }

    @Override
    public void onFailure(String s) {
        view.showFailMessage(s);
    }

    @Override
    public void onFailConnect() {
        view.onFailConnection();
    }

    @Override
    public void onFailConfEmail(int code) {
        view.onFailConfirm(code);
    }

    @Override
    public void onFailChangePassword() {
        view.onFailChangePas();
    }

    @Override
    public void onSuccessSendEmailConfirm() {
        view.onSuccessSend();
    }

    @Override
    public void onSuccessChangePassword() {
        view.onSuccessChangePasswordMessage();
    }
}

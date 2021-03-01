package com.studenttomsk.upYourParty.Views.Fragments.MenegerAnnoun;

import com.studenttomsk.upYourParty.Classes.ClassId;
import com.studenttomsk.upYourParty.Classes.AnnounceClass;

import java.util.List;

public class ManagerAnnounPresenter implements ManagerAnnounMethods.PresenterManagerAnnoun, ManagerAnnounMethods.ModelManagerAnnoun.OnFinishedListener {
    ManagerAnnounMethods.ViewManagerAnnoun view;
    ManagerAnnounMethods.ModelManagerAnnoun model;

    public ManagerAnnounPresenter(ManagerAnnounMethods.ViewManagerAnnoun view, ManagerAnnounMethods.ModelManagerAnnoun model) {
        this.view = view;
        this.model = model;
    }


    @Override
    public void getMyAnnuncements(String token) {
        model.getMyAnnouncements(this,token);
    }

    @Override
    public void deleteMyAnnounce(ClassId classId) {
        model.deleteAds(this,classId);
    }

    @Override
    public void setPostAnnounceInfo(long id) {
        model.getAnnounceInfo(this,id);
    }

    @Override
    public void onSuccessLoadAnnounceInfo(AnnounceClass announce) {
        view.onSuccessLoadInfoView(announce);
    }

    @Override
    public void onSuccess(List<AnnounceClass> recycleMyAnnouncementItem) {
        view.onSuccessListener(recycleMyAnnouncementItem);
    }

    @Override
    public void onSuccessDelete() {
        view.onSuccessDeleteAds();
    }

    @Override
    public void onFailure(String s) {
        view.onFailListener();
    }
}

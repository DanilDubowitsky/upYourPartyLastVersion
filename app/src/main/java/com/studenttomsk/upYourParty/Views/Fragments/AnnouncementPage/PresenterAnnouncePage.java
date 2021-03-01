package com.studenttomsk.upYourParty.Views.Fragments.AnnouncementPage;

import com.studenttomsk.upYourParty.Classes.AnnounceClass;
import com.studenttomsk.upYourParty.Classes.AddFavorites;
import com.studenttomsk.upYourParty.Classes.ClassId;
import com.studenttomsk.upYourParty.Views.Fragments.AllAnnouncements.AllAnnouncementsMethods;
import com.studenttomsk.upYourParty.Classes.AdsSearchFilterDto;
import com.studenttomsk.upYourParty.Classes.SearchAnnouncementRes;

import java.util.ArrayList;

public class PresenterAnnouncePage implements AllAnnouncementsMethods.AllAnnounPresentser, AllAnnouncementsMethods.AllAnnounModel.OnFinishedListener {
    AllAnnouncementsMethods.AllAnnounView view;
    AllAnnouncementsMethods.AllAnnounModel model;

    public PresenterAnnouncePage(AllAnnouncementsMethods.AllAnnounView view, AllAnnouncementsMethods.AllAnnounModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void postToSearch(AdsSearchFilterDto ads) {

    }

    @Override
    public void setToServer(long id) {
        model.getAnnounceById(this,id);
    }

    @Override
    public void setToFavor(AddFavorites id, String token) {
        model.setServerRequestFavorites(this,id, token);
    }

    @Override
    public void onFinishFav(ClassId classId) {
        view.onFinishAddToFavor(classId);
    }

    @Override
    public void onFinished(ArrayList<SearchAnnouncementRes> searchAnnouncementResList) {

    }

    @Override
    public void onFinish(AnnounceClass announcementClass) {
        view.showAnnounce(announcementClass);
    }

    @Override
    public void onFailure() {
        view.showError();
    }
}

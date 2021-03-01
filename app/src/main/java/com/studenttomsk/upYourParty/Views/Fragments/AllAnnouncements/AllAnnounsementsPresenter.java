package com.studenttomsk.upYourParty.Views.Fragments.AllAnnouncements;

import com.studenttomsk.upYourParty.Classes.AddFavorites;
import com.studenttomsk.upYourParty.Classes.AnnounceClass;
import com.studenttomsk.upYourParty.Classes.AdsSearchFilterDto;
import com.studenttomsk.upYourParty.Classes.ClassId;
import com.studenttomsk.upYourParty.Classes.SearchAnnouncementRes;

import java.util.ArrayList;

public class AllAnnounsementsPresenter implements AllAnnouncementsMethods.AllAnnounPresentser, AllAnnouncementsMethods.AllAnnounModel.OnFinishedListener {
    AllAnnouncementsMethods.AllAnnounModel model;
    AllAnnouncementsMethods.AllAnnounView view;


    public AllAnnounsementsPresenter(AllAnnouncementsMethods.AllAnnounModel model, AllAnnouncementsMethods.AllAnnounView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void postToSearch(AdsSearchFilterDto ads) {
        model.search(this,ads);
    }

    @Override
    public void setToServer(long id) {

    }

    @Override
    public void setToFavor(AddFavorites id, String token) {

    }

    @Override
    public void onFinishFav(ClassId classId) {
        view.onFinishAddToFavor(classId);
    }

    @Override
    public void onFinished(ArrayList<SearchAnnouncementRes> searchAnnouncementResList) {
        view.toSearchResultsFragment(searchAnnouncementResList);
    }

    @Override
    public void onFinish(AnnounceClass announcementClass) {

    }

    @Override
    public void onFailure() {

    }
}

package com.studenttomsk.upYourParty.Views.Main;

import com.studenttomsk.upYourParty.Classes.AdsSearchFilterDto;
import com.studenttomsk.upYourParty.Classes.ProfileNumbNameTel;
import com.studenttomsk.upYourParty.Classes.SearchAnnouncementRes;

import java.util.ArrayList;

public class MainActivityPresenter implements MainMetods.Presenter, MainMetods.Model.OnFinishedListener{
    private MainMetods.View view;
    private MainMetods.Model model;

    public MainActivityPresenter(MainMetods.View view, MainMetods.Model model) {
        this.view = view;
        this.model = model;
    }


    @Override
    public void obFinishedGetProfile(ProfileNumbNameTel info) {
        view.setInformation(info);
    }

    @Override
    public void onFinished(ArrayList<SearchAnnouncementRes> searchAnnouncementResList) {
        view.toSearchResultsFragment(searchAnnouncementResList);
    }

    @Override
    public void onFailure(String t) {
        view.showFailMessage(t);
    }

    @Override
    public void postToSearch(AdsSearchFilterDto ads) {
        model.search(this,ads);
    }

    @Override
    public void postGetProfile() {
        model.getProfileInfo(this);
    }
}

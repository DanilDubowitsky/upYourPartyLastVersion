package com.studenttomsk.upYourParty.Network;

import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.AddFavorites;
import com.studenttomsk.upYourParty.Views.Fragments.AllAnnouncements.AllAnnouncementsMethods;
import com.studenttomsk.upYourParty.Classes.AdsSearchFilterDto;
import com.studenttomsk.upYourParty.Classes.SearchAnnouncementRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelAllAnnouncements implements AllAnnouncementsMethods.AllAnnounModel {
    @Override
    public void search(OnFinishedListener onFinishedListener, AdsSearchFilterDto ads) {
        NetworkService.getInstance().getJSONApi().searchAds(ads, Singleton.getInstance().GetToken())
                .enqueue(new Callback<ArrayList<SearchAnnouncementRes>>() {
                    @Override
                    public void onResponse(Call<ArrayList<SearchAnnouncementRes>> call, Response<ArrayList<SearchAnnouncementRes>> response) {
                        if(response.isSuccessful()){
                            onFinishedListener.onFinished(response.body());
                        }
                        else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<SearchAnnouncementRes>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void getAnnounceById(OnFinishedListener onFinishedListener, long id) {

    }

    @Override
    public void setServerRequestFavorites(OnFinishedListener onFinishedListener, AddFavorites id, String token) {

    }
}

package com.studenttomsk.upYourParty.Network;

import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.ProfileNumbNameTel;
import com.studenttomsk.upYourParty.Classes.AdsSearchFilterDto;
import com.studenttomsk.upYourParty.Views.Main.MainMetods;
import com.studenttomsk.upYourParty.Classes.SearchAnnouncementRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostSearchModel implements MainMetods.Model {

    @Override
    public void search(final OnFinishedListener onFinishedListener, AdsSearchFilterDto ads) {
        NetworkService.getInstance().getJSONApi().searchAds(ads, Singleton.getInstance().GetToken())
                .enqueue(new Callback<ArrayList<SearchAnnouncementRes>>() {
                    @Override
                    public void onResponse(Call<ArrayList<SearchAnnouncementRes>> call, Response<ArrayList<SearchAnnouncementRes>> response) {
                        if(response.isSuccessful()){
                            onFinishedListener.onFinished(response.body());
                        }
                        else {
                            onFinishedListener.onFailure("Ошибка");
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<SearchAnnouncementRes>> call, Throwable t) {
                        onFinishedListener.onFailure(t.getMessage());
                    }
                });
    }

    @Override
    public void getProfileInfo(OnFinishedListener onFinishedListener) {
        NetworkService
                .getInstance()
                .getJSONApi()
                .getProfileInfo(Singleton.getInstance().GetToken())
                .enqueue(new Callback<ProfileNumbNameTel>() {
                    @Override
                    public void onResponse(Call<ProfileNumbNameTel> call, Response<ProfileNumbNameTel> response) {
                        if(response.isSuccessful()){
                            onFinishedListener.obFinishedGetProfile(response.body());
                        }
                        else{

                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileNumbNameTel> call, Throwable t) {

                    }
                });


    }
}

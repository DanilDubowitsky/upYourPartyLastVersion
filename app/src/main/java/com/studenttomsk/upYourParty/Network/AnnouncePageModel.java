package com.studenttomsk.upYourParty.Network;

import com.studenttomsk.upYourParty.Classes.ClassId;
import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.AddFavorites;
import com.studenttomsk.upYourParty.Views.Fragments.AllAnnouncements.AllAnnouncementsMethods;
import com.studenttomsk.upYourParty.Classes.AnnounceClass;
import com.studenttomsk.upYourParty.Classes.AdsSearchFilterDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnnouncePageModel implements AllAnnouncementsMethods.AllAnnounModel {


    @Override
    public void search(OnFinishedListener onFinishedListener, AdsSearchFilterDto ads) {

    }

    @Override
    public void getAnnounceById(final OnFinishedListener onFinishedListener, long id) {
        NetworkService.getInstance().getJSONApi().getAnnounceById(id, Singleton.getInstance().GetToken())
                .enqueue(new Callback<AnnounceClass>() {
                    @Override
                    public void onResponse(Call<AnnounceClass> call, Response<AnnounceClass> response) {
                        if(response.isSuccessful()){
                            onFinishedListener.onFinish(response.body());
                        }
                        else{
                            onFinishedListener.onFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<AnnounceClass> call, Throwable t) {
                        onFinishedListener.onFailure();
                    }
                });
    }

    @Override
    public void setServerRequestFavorites(final OnFinishedListener onFinishedListener, AddFavorites id, String token) {
        NetworkService.getInstance().getJSONApi()
                .addToFavorites(id,token)
                .enqueue(new Callback<ClassId>() {
                    @Override
                    public void onResponse(Call<ClassId> call, Response<ClassId> response) {
                        if(response.isSuccessful()){
                            onFinishedListener.onFinishFav(response.body());
                        }
                        else{
                            onFinishedListener.onFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<ClassId> call, Throwable t) {
                        onFinishedListener.onFailure();
                    }
                });
    }
}

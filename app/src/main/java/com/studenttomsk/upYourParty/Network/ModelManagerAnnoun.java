package com.studenttomsk.upYourParty.Network;

import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.ClassId;
import com.studenttomsk.upYourParty.Classes.AnnounceClass;
import com.studenttomsk.upYourParty.Views.Fragments.MenegerAnnoun.ManagerAnnounMethods;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelManagerAnnoun implements ManagerAnnounMethods.ModelManagerAnnoun {


    @Override
    public void deleteAds(OnFinishedListener onFinishedListener, ClassId classId) {
        NetworkService
                .getInstance()
                .getJSONApi()
                .deleteAds(classId,Singleton.getInstance().GetToken())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            onFinishedListener.onSuccessDelete();
                        }
                        else{
                            onFinishedListener.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        onFinishedListener.onFailure(t.getMessage());
                    }
                });
    }

    @Override
    public void getAnnounceInfo(final OnFinishedListener onFinishedListener, long id) {
        NetworkService.getInstance().getJSONApi().getAnnounceById(id, Singleton.getInstance().GetToken())
                .enqueue(new Callback<AnnounceClass>() {
                    @Override
                    public void onResponse(Call<AnnounceClass> call, Response<AnnounceClass> response) {
                        if(response.isSuccessful()) {
                            onFinishedListener.onSuccessLoadAnnounceInfo(response.body());
                        }
                        else{
                            onFinishedListener.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<AnnounceClass> call, Throwable t) {
                        onFinishedListener.onFailure(t.getMessage());
                    }
                });
    }

    @Override
    public void getMyAnnouncements(final OnFinishedListener onFinishedListener, String token) {
        NetworkService.getInstance().getJSONApi().getMyAnnouncements(token)
                .enqueue(new Callback<List<AnnounceClass>>() {
                    @Override
                    public void onResponse(Call<List<AnnounceClass>> call, Response<List<AnnounceClass>> response) {
                        if(response.isSuccessful()) {
                            onFinishedListener.onSuccess(response.body());
                        }
                        else{
                            onFinishedListener.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AnnounceClass>> call, Throwable t) {
                        onFinishedListener.onFailure(t.getMessage());
                    }
                });
    }
}

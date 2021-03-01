package com.studenttomsk.upYourParty.Network;

import com.studenttomsk.upYourParty.Views.Fragments.AnnouncementPage.AnnouncementPageMethods;
import com.studenttomsk.upYourParty.Classes.DidClass;
import com.studenttomsk.upYourParty.Classes.RoomChatDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelCreateRoom implements AnnouncementPageMethods.AnnouncePageModel {
    @Override
    public void createRoom(OnFinishedListener onF, RoomChatDto roomChatDto, String token) {
        NetworkService
                .getInstance()
                .getJSONApi()
                .createRoom(roomChatDto,token)
                .enqueue(new Callback<DidClass>() {
                    @Override
                    public void onResponse(Call<DidClass> call, Response<DidClass> response) {
                        if(response.isSuccessful()){
                            onF.onFinishCreate(response.body());
                        }
                        else
                            onF.onFailure(response.message());
                    }

                    @Override
                    public void onFailure(Call<DidClass> call, Throwable t) {
                        onF.onFailure(t.getMessage());
                    }
                });
    }
}

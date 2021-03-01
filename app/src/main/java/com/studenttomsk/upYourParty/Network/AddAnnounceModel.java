package com.studenttomsk.upYourParty.Network;

import com.studenttomsk.upYourParty.Views.Fragments.AddAnnoun.AddAnnounMethods;
import com.studenttomsk.upYourParty.Classes.AddAnnounceClass;
import com.studenttomsk.upYourParty.Classes.AnnouncementClass;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAnnounceModel implements AddAnnounMethods.Model {
    @Override
    public void addAnnounce(final OnCompleteListener onCompleteListener, AddAnnounceClass announce, String token) {
       NetworkService.getInstance().getJSONApi().addAnnouncement(announce,token)
        .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    onCompleteListener.onFinish();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onCompleteListener.onFailure(t.getMessage());
            }
        });

    }

    @Override
    public void postImage(final OnCompleteListener onCompleteListener, MultipartBody.Part image) {
        NetworkService.getInstance()
                .getJSONApi()
                .uploadImage(image)
                .enqueue(new Callback<Map<String,String>>() {
                    @Override
                    public void onResponse(Call<Map<String,String>> call, Response<Map<String,String>> response) {
                        if(response.isSuccessful()){
                            onCompleteListener.onFinishUpload(response.body());
                        }
                        else{
                            onCompleteListener.onFailure(response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<Map<String,String>> call, Throwable t) {
                        onCompleteListener.onFailure(t.getMessage());
                    }
                });
    }


    @Override
    public void deleteImage(final OnCompleteListener onCompleteListener, List<String> name) {
        NetworkService.getInstance().getJSONApi().deleteImage(name)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            onCompleteListener.onFinishDelete();
                        }
                        else{
                            onCompleteListener.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        onCompleteListener.onFailure(t.getMessage());
                    }
                });
    }

    @Override
    public void changeAnnounce(final OnCompleteListener onCompleteListener, AnnouncementClass announcementClass, String token) {
        NetworkService.getInstance().getJSONApi()
                .changeAnnounce(announcementClass,token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        onCompleteListener.onFinishChange();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        onCompleteListener.onFailure(t.getMessage());
                    }
                });
    }
}

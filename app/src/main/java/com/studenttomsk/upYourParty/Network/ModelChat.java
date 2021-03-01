package com.studenttomsk.upYourParty.Network;

import com.studenttomsk.upYourParty.Network.NetworkService;
import com.studenttomsk.upYourParty.Views.Fragments.Chat.ChatMethods;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelChat implements ChatMethods.ChatModel {

    @Override
    public void postImage(OnCompleteListener onCompleteListener, MultipartBody.Part image) {
        NetworkService.getInstance()
                .getJSONApi()
                .uploadImage(image)
                .enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        if(response.isSuccessful()){
                            onCompleteListener.onFinishUpload(response.body());
                        }
                        else{
                            onCompleteListener.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
                        onCompleteListener.onFailure(t.getMessage());
                    }
                });
    }
}

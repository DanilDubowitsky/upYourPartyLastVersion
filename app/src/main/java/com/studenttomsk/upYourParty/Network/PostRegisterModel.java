package com.studenttomsk.upYourParty.Network;

import com.studenttomsk.upYourParty.Views.Register.RegisterMethods;
import com.studenttomsk.upYourParty.Classes.RegistrationClass;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRegisterModel implements RegisterMethods.Model {
    @Override
    public void registerUser(final OnFinishedListener onFinishedListener, RegistrationClass registrationClass) {
        Call<ResponseBody> call = NetworkService.getInstance().getJSONApi().reg(registrationClass);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished();
                } else {
                   onFinishedListener.onFailure(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });

    }
}

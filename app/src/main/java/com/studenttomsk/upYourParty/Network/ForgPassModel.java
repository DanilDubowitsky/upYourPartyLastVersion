package com.studenttomsk.upYourParty.Network;

import com.studenttomsk.upYourParty.Classes.ClassEmail;
import com.studenttomsk.upYourParty.Views.ForgPass.ForgPassMethods;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgPassModel implements ForgPassMethods.ForgPassModel {
    @Override
    public void sendForgPassMessage(OnFinishedListener onFinishedListener, ClassEmail email) {
        NetworkService
                .getInstance()
                .getJSONApi()
                .resetPass(email)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful())
                            onFinishedListener.onFinished();
                        else
                            onFinishedListener.onFailure(response.code());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }
}

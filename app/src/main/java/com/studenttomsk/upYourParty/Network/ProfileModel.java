package com.studenttomsk.upYourParty.Network;

import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.ChangeProfileClass;
import com.studenttomsk.upYourParty.Classes.PasswordClass;
import com.studenttomsk.upYourParty.Views.Fragments.Profile.ProfileMethods;
import com.studenttomsk.upYourParty.Classes.ProfileNumbNameTel;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileModel implements ProfileMethods.ModelProfile {

    @Override
    public void confEmail(onCompleteListener onCompleteListener) {
        NetworkService
                .getInstance()
                .getJSONApi()
                .confirmEmail(Singleton.getInstance().GetToken())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            onCompleteListener.onSuccessSendEmailConfirm();
                        }
                        else{
                            onCompleteListener.onFailConfEmail(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        onCompleteListener.onFailConnect();
                    }
                });
    }

    @Override
    public void loadImageProfile(onCompleteListener onCompleteListener, MultipartBody.Part img) {
        NetworkService
                .getInstance()
                .getJSONApi()
                .loadProfileImage(img,Singleton.getInstance().GetToken())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            onCompleteListener.onFinishedLoadImage(response.message());
                        }
                        else {
                            onCompleteListener.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        onCompleteListener.onFailConnect();
                    }
                });
    }

    @Override
    public void getProfileInfo(final onCompleteListener onCompleteListener, String token) {
        NetworkService.getInstance()
                .getJSONApi()
                .getProfileInfo(token)
                .enqueue(new Callback<ProfileNumbNameTel>() {
                    @Override
                    public void onResponse(Call<ProfileNumbNameTel> call, Response<ProfileNumbNameTel> response) {
                        if(response.isSuccessful()){
                            onCompleteListener.onSuccessGetInfo(response.body());
                        }
                        else{
                            onCompleteListener.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileNumbNameTel> call, Throwable t) {
                        onCompleteListener.onFailConnect();
                    }
                });
    }

    @Override
    public void changeProfile(final onCompleteListener onCompleteListener, ChangeProfileClass changeProfileClass, String token) {
        NetworkService.getInstance().getJSONApi().changeProfile(changeProfileClass, token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            onCompleteListener.onSuccess(response.message());
                        }
                        else{
                            onCompleteListener.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        onCompleteListener.onSuccess(t.getMessage());
                    }
                });
    }

    @Override
    public void changePassword(final onCompleteListener onCompleteListener, PasswordClass passwordClass, String token) {
        NetworkService.getInstance()
                .getJSONApi()
                .changePassword(passwordClass, token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            onCompleteListener.onSuccessChangePassword();
                        }
                        else
                            onCompleteListener.onFailChangePassword();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        onCompleteListener.onFailConnect();
                    }
                });
    }
}

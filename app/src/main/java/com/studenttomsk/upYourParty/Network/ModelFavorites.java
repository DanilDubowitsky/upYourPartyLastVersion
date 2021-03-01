package com.studenttomsk.upYourParty.Network;

import com.studenttomsk.upYourParty.Classes.AddFavorites;
import com.studenttomsk.upYourParty.Classes.ClassFavorites;
import com.studenttomsk.upYourParty.Views.Fragments.Favorites.FavoritesMethods;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelFavorites implements FavoritesMethods.ModelFav {


    @Override
    public void getFavorites(final OnFinishedListener onFinishedListener, String token) {
        NetworkService
                .getInstance()
                .getJSONApi()
                .getMyFavorites(token)
                .enqueue(new Callback<ArrayList<ClassFavorites>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ClassFavorites>> call, Response<ArrayList<ClassFavorites>> response) {
                        if(response.isSuccessful()){
                            onFinishedListener.onFinished(response.body());
                        }
                        else{
                            onFinishedListener.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ClassFavorites>> call, Throwable t) {
                        onFinishedListener.onFailure(t.getMessage());
                    }
                });
    }

    @Override
    public void deleteFav(final OnFinishedListener onFinishedListener, long id, String token) {
        NetworkService
                .getInstance()
                .getJSONApi()
                .removeFavorites(new AddFavorites(id),token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            onFinishedListener.onFinishedDelete();
                        }
                        else
                            onFinishedListener.onFailure(response.message());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        onFinishedListener.onFailure(t.getMessage());
                    }
                });
    }
}

package com.studenttomsk.upYourParty.Views.Fragments.Favorites;

import com.studenttomsk.upYourParty.Classes.ClassFavorites;

import java.util.ArrayList;

public class FavoritesPresenter implements FavoritesMethods.PresenterFav, FavoritesMethods.ModelFav.OnFinishedListener{
    FavoritesMethods.ViewFav view;
    FavoritesMethods.ModelFav model;

    public FavoritesPresenter(FavoritesMethods.ViewFav view, FavoritesMethods.ModelFav model) {
        this.view = view;
        this.model = model;
    }


    @Override
    public void getAllFavorites(String token) {
        model.getFavorites(this,token);
    }

    @Override
    public void deleteFromFav(String token, long id) {
        model.deleteFav(this,id,token);
    }


    @Override
    public void onFinishedDelete() {
        view.showMessageSuccess();
    }

    @Override
    public void onFinished(ArrayList<ClassFavorites> res) {
        view.showResults(res);
    }

    @Override
    public void onFailure(String s) {

    }
}

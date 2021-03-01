package com.studenttomsk.upYourParty.Views.Fragments.Favorites;

import com.studenttomsk.upYourParty.Classes.ClassFavorites;

import java.util.ArrayList;

public interface FavoritesMethods {

    interface ViewFav{
        void showResults(ArrayList<ClassFavorites> res);
        void showMessageSuccess();
    }
    interface PresenterFav{
        void getAllFavorites(String token);
        void deleteFromFav(String token, long id);
    }
    interface ModelFav{
        interface OnFinishedListener {
            void onFinishedDelete();
            void onFinished(ArrayList<ClassFavorites> res);
            void onFailure(String s);
        }
        void getFavorites(OnFinishedListener onFinishedListener, String token);
        void deleteFav(OnFinishedListener onFinishedListener, long id, String token);
    }

}

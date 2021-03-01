package com.studenttomsk.upYourParty.Views.Fragments.Categories;

import com.studenttomsk.upYourParty.Classes.AdsSearchFilterDto;
import com.studenttomsk.upYourParty.Classes.SearchAnnouncementRes;

import java.util.ArrayList;

public interface CategoriesMethods {
    interface View{
        void toSearchResultsFragment(ArrayList<SearchAnnouncementRes> searchAnnouncementRes);
        void showDialog();
        void hideDialog();
        void showFailMessage(String s);
    }
    interface Presenter{
        void postToSearch(AdsSearchFilterDto ads);
    }
    interface Model {
        interface OnFinishedListener {
            void onFinished(ArrayList<SearchAnnouncementRes> searchAnnouncementResList);
            void onFailure(String s);
        }

        void search(OnFinishedListener onFinishedListener, AdsSearchFilterDto ads);
    }


}

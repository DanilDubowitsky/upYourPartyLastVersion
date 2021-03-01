package com.studenttomsk.upYourParty.Views.Fragments.AllAnnouncements;

import com.studenttomsk.upYourParty.Classes.AddFavorites;
import com.studenttomsk.upYourParty.Classes.AnnounceClass;
import com.studenttomsk.upYourParty.Classes.AdsSearchFilterDto;
import com.studenttomsk.upYourParty.Classes.ClassId;
import com.studenttomsk.upYourParty.Classes.SearchAnnouncementRes;

import java.util.ArrayList;

public interface AllAnnouncementsMethods {
    interface AllAnnounView{
        void showAnnounce(AnnounceClass announcementClass);
        void onFinishAddToFavor(ClassId classId);
        void toSearchResultsFragment(ArrayList<SearchAnnouncementRes> searchAnnouncementRes);
        void showError();
    }
    interface AllAnnounPresentser{
        void postToSearch(AdsSearchFilterDto ads);
        void setToServer(long id);
        void setToFavor(AddFavorites id, String token);
    }
    interface AllAnnounModel{
        interface OnFinishedListener{
            void onFinishFav(ClassId id);
            void onFinished(ArrayList<SearchAnnouncementRes> searchAnnouncementResList);
            void onFinish(AnnounceClass announcementClass);
            void onFailure();
        }

        void search(OnFinishedListener onFinishedListener, AdsSearchFilterDto ads);
        void getAnnounceById(OnFinishedListener onFinishedListener, long id);
        void setServerRequestFavorites(OnFinishedListener onFinishedListener, AddFavorites id, String token);
    }
}

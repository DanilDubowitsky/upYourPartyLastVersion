package com.studenttomsk.upYourParty.Views.Main;

import com.studenttomsk.upYourParty.Classes.AdsSearchFilterDto;
import com.studenttomsk.upYourParty.Classes.ProfileNumbNameTel;
import com.studenttomsk.upYourParty.Classes.SearchAnnouncementRes;

import java.util.ArrayList;

public interface MainMetods {

    interface View{
        void setInformation(ProfileNumbNameTel info);
        void toSearchResultsFragment(ArrayList<SearchAnnouncementRes> searchAnnouncementRes);
        void showDialog();
        void hideDialog();
        void showFailMessage(String s);
    }
    interface Presenter{
        void postToSearch(AdsSearchFilterDto ads);
        void postGetProfile();
    }
    interface Model {
        interface OnFinishedListener {
            void obFinishedGetProfile(ProfileNumbNameTel info);
            void onFinished(ArrayList<SearchAnnouncementRes> searchAnnouncementResList);
            void onFailure(String s);
        }
        void search(MainMetods.Model.OnFinishedListener onFinishedListener, AdsSearchFilterDto ads);
        void getProfileInfo(OnFinishedListener onFinishedListener);
    }

}

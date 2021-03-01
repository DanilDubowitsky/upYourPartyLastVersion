package com.studenttomsk.upYourParty.Views.Fragments.MenegerAnnoun;

import com.studenttomsk.upYourParty.Classes.ClassId;
import com.studenttomsk.upYourParty.Classes.AnnounceClass;

import java.util.List;

public interface ManagerAnnounMethods {

    interface ViewManagerAnnoun{
        void onSuccessListener(List<AnnounceClass> recycleMyAnnouncementItem);
        void onSuccessLoadInfoView(AnnounceClass announceClass);
        void onFailListener();
        void onSuccessDeleteAds();
    }

    interface PresenterManagerAnnoun{
        void getMyAnnuncements(String token);
        void deleteMyAnnounce(ClassId classId);
        void setPostAnnounceInfo(long id);
    }

    interface ModelManagerAnnoun{
        interface OnFinishedListener{
            void onSuccessLoadAnnounceInfo(AnnounceClass announce);
            void onSuccess(List<AnnounceClass> recycleMyAnnouncementItem);
            void onSuccessDelete();
            void onFailure(String s);
        }
        void deleteAds(OnFinishedListener onFinishedListener, ClassId classId);
        void getAnnounceInfo(ManagerAnnounMethods.ModelManagerAnnoun.OnFinishedListener onFinishedListener, long id);
        void getMyAnnouncements(ManagerAnnounMethods.ModelManagerAnnoun.OnFinishedListener onFinishedListener, String token);
    }

}

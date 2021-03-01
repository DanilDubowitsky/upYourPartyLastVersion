package com.studenttomsk.upYourParty.Views.Fragments.AddAnnoun;

import com.studenttomsk.upYourParty.Classes.AddAnnounceClass;
import com.studenttomsk.upYourParty.Classes.AnnouncementClass;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public interface AddAnnounMethods {

    interface View{
        void onSuccessChange();
        void onSuccessDelete();

        void successUploadImage(Map<String, String> name);
        void showMessage();
        void onFailMessage(String message);
        void pdShow();
        void pdHied();
    }

    interface Presenter{
        void setPostAddAnnounce(AddAnnounceClass announce, String token);
        void setImagePost(MultipartBody.Part image);
        void deleteImagePost(List<String> name);
        void setPostChangeAnnounce(AnnouncementClass announce, String token);
    }


    interface Model{
        interface OnCompleteListener{
            void onFinish();
            void onFinishDelete();
            void onFinishChange();
            void onFinishUpload(Map<String, String> name);
            void onFailure(String t);
        }
        void addAnnounce(OnCompleteListener onCompleteListener, AddAnnounceClass announce, String token);
        void postImage(OnCompleteListener onCompleteListener, MultipartBody.Part image);
        void deleteImage(OnCompleteListener onCompleteListener, List<String> name);
        void changeAnnounce(OnCompleteListener onCompleteListener, AnnouncementClass announcementClass, String token);
    }

}

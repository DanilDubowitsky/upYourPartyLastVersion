package com.studenttomsk.upYourParty.Views.Fragments.Profile;

import com.studenttomsk.upYourParty.Classes.ChangeProfileClass;
import com.studenttomsk.upYourParty.Classes.PasswordClass;
import com.studenttomsk.upYourParty.Classes.ProfileNumbNameTel;

import okhttp3.MultipartBody;

public interface ProfileMethods {

    interface ViewProfile{
        void onSuccessChangePasswordMessage();
        void onSuccessLoadInfo(ProfileNumbNameTel info);
        void showSuccessMessage(String message);
        void showFailMessage(String message);
        void onFailChangePas();
        void successUploadImage(String t);
        void onFailConfirm(int code);
        void onSuccessSend();
        void onFailConnection();

    }
    interface PresenterProfile{
        void setRequestProfileInfo(String token);
        void setConfEmail();
        void setChangePost(ChangeProfileClass changeProfileClass, String token);
        void loadImage(MultipartBody.Part image);
        void setChangePasswordRequest(PasswordClass passwordClass, String token);
    }
    interface ModelProfile{
        interface onCompleteListener{
            void onSuccessGetInfo(ProfileNumbNameTel info);
            void onSuccess(String message);
            void onFinishedLoadImage(String t);
            void onFailure(String message);
            void onFailConnect();
            void onFailConfEmail(int code);
            void onFailChangePassword();
            void onSuccessSendEmailConfirm();
            void onSuccessChangePassword();
        }
        void confEmail(onCompleteListener onCompleteListener);
        void loadImageProfile(onCompleteListener onCompleteListener, MultipartBody.Part image);
        void getProfileInfo(onCompleteListener onCompleteListener, String token);
        void changeProfile(onCompleteListener onCompleteListener, ChangeProfileClass changeProfileClass, String token);
        void changePassword(onCompleteListener onCompleteListener, PasswordClass passwordClass, String token);
    }

}

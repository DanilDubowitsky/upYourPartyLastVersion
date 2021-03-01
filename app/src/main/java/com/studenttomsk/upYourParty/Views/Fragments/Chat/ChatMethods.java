package com.studenttomsk.upYourParty.Views.Fragments.Chat;

import java.util.Map;

import okhttp3.MultipartBody;

public interface ChatMethods {
    interface ChatView{
        void onSuccessChange(Map<String, String> name);
        void onFail(String string);
    }

    interface ChatPresenter{
        void setImagePost(MultipartBody.Part image);
    }


    interface ChatModel{
        interface OnCompleteListener{
            void onFinishUpload(Map<String, String> name);
            void onFailure(String t);
        }
        void postImage(OnCompleteListener onCompleteListener, MultipartBody.Part image);
    }

}

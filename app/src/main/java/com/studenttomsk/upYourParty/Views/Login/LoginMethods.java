package com.studenttomsk.upYourParty.Views.Login;

import com.studenttomsk.upYourParty.Classes.AuthorizationRequestDto;

import java.util.Map;

public interface LoginMethods {
    interface View {
        void setToSharedPreferences(Map<String,String>map);
        void showPd();
        void hidePd();
        void onResponseFailRule(String s);

    }
    interface Presenter{

        void sendPostToLoginUser(AuthorizationRequestDto authorizationRequestDto);
    }
    interface Model{
        interface OnFinishedListener{

            void onFinished(Map<String,String> map);
            void onFailure(String s);
        }

        void LoginUser(OnFinishedListener onFinishedListener, AuthorizationRequestDto authorizationRequestDto);
    }


}

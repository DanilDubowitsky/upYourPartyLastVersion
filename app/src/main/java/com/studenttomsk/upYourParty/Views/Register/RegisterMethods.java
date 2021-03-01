package com.studenttomsk.upYourParty.Views.Register;

import com.studenttomsk.upYourParty.Classes.RegistrationClass;

public interface RegisterMethods {
    interface View{
        void toLoginActivity();
        void showPd();
        void hidePd();
        void onResponseFailRule(String t);
    }

    interface Presenter{
        void sendPostToRegisterUser(RegistrationClass registrationClass);
    }

    interface Model{
        interface OnFinishedListener{
            void onFinished();
            void onFailure(String t);
        }
        void registerUser(OnFinishedListener onFinishedListener,RegistrationClass registrationClass);
    }

}

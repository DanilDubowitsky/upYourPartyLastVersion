package com.studenttomsk.upYourParty.Views.ForgPass;

import com.studenttomsk.upYourParty.Classes.ClassEmail;

public interface ForgPassMethods {
    interface ForgPassView{
        void onSuccessReset();
        void onFailrule(int code);
    }

    interface ForgPassPresenter{
        void sendFogPass(ClassEmail email);
    }

    interface ForgPassModel{
        interface OnFinishedListener{
            void onFinished();
            void onFailure(int code);
        }

        void sendForgPassMessage(OnFinishedListener onFinishedListener, ClassEmail email);
    }
}

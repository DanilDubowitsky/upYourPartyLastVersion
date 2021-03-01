package com.studenttomsk.upYourParty.Views.ForgPass;

import com.studenttomsk.upYourParty.Classes.ClassEmail;

public class ForgPassPresenter implements ForgPassMethods.ForgPassPresenter,ForgPassMethods.ForgPassModel.OnFinishedListener {
    ForgPassMethods.ForgPassView view;
    ForgPassMethods.ForgPassModel model;

    public ForgPassPresenter(ForgPassMethods.ForgPassView view, ForgPassMethods.ForgPassModel model) {
        this.view = view;
        this.model = model;
    }



    @Override
    public void onFinished() {
        view.onSuccessReset();
    }

    @Override
    public void onFailure(int t) {
        view.onFailrule(t);
    }

    @Override
    public void sendFogPass(ClassEmail email) {
        model.sendForgPassMessage(this,email);
    }
}

package com.studenttomsk.upYourParty.Views.Register;

import com.studenttomsk.upYourParty.Classes.RegistrationClass;

public class RegisterPresenter implements RegisterMethods.Presenter, RegisterMethods.Model.OnFinishedListener {
    RegisterMethods.View view;
    RegisterMethods.Model model;

    public RegisterPresenter(RegisterMethods.View view, RegisterMethods.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void sendPostToRegisterUser(RegistrationClass registrationClass) {
        model.registerUser(this,registrationClass);
    }

    @Override
    public void onFinished() {
        view.toLoginActivity();
    }

    @Override
    public void onFailure(String t) {
        view.onResponseFailRule(t);
    }
}

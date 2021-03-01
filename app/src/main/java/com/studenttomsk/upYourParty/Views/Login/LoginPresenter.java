package com.studenttomsk.upYourParty.Views.Login;

import com.studenttomsk.upYourParty.Classes.AuthorizationRequestDto;

import java.util.Map;

public class LoginPresenter implements LoginMethods.Presenter, LoginMethods.Model.OnFinishedListener {
    private LoginMethods.View view;
    private LoginMethods.Model model;

    public LoginPresenter(LoginMethods.View view, LoginMethods.Model model) {
        this.view = view;
        this.model = model;
    }



    @Override
    public void sendPostToLoginUser(AuthorizationRequestDto authorizationRequestDto) {
        model.LoginUser(this, authorizationRequestDto);
    }



    @Override
    public void onFinished(Map<String, String> map) {
        if(map!=null){
            view.setToSharedPreferences(map);
        }
    }

    @Override
    public void onFailure(String t) {
        view.onResponseFailRule(t);
    }
}

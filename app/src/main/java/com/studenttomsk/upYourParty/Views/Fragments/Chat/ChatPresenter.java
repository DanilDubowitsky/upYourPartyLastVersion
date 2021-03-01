package com.studenttomsk.upYourParty.Views.Fragments.Chat;

import java.util.Map;

import okhttp3.MultipartBody;

public class ChatPresenter implements ChatMethods.ChatPresenter, ChatMethods.ChatModel.OnCompleteListener {
    ChatMethods.ChatView view;
    ChatMethods.ChatModel model;

    public ChatPresenter(ChatMethods.ChatView view, ChatMethods.ChatModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void setImagePost(MultipartBody.Part image) {
        model.postImage(this,image);
    }

    @Override
    public void onFinishUpload(Map<String, String> name) {
        view.onSuccessChange(name);
    }

    @Override
    public void onFailure(String t) {
        view.onFail(t);
    }
}

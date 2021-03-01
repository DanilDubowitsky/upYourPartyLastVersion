package com.studenttomsk.upYourParty.Views.Fragments.Messages;

import com.studenttomsk.upYourParty.Classes.AllMessagesClass;
import com.studenttomsk.upYourParty.Classes.ChatsItemClass;
import com.studenttomsk.upYourParty.Classes.DidClass;
import com.studenttomsk.upYourParty.Classes.Singleton;

import java.util.List;

public class MessagesPresenter implements MessagesMethods.MessagesPresenter, MessagesMethods.MessagesModel.OnFinishedListener {
    MessagesMethods.MessagesView view;
    MessagesMethods.MessagesModel model;

    public MessagesPresenter(MessagesMethods.MessagesView view, MessagesMethods.MessagesModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void postMyMessages() {
        model.getMyMessages(this, Singleton.getInstance().GetToken());
    }

    @Override
    public void postMyEmail() {
        model.getMyEmail(this, Singleton.getInstance().GetToken());
    }

    @Override
    public void redMes(DidClass did) {
        model.readMessage(this,did);
    }

    @Override
    public void getMessages(DidClass did) {
        model.getAllMesages(this,did);
    }

    @Override
    public void onFinishedEmail(String email) {
        view.onSuccessEmail(email);
    }

    @Override
    public void onFinishedReading() {
        view.onSuccessReadMessage();
    }

    @Override
    public void onFinishedLoadMessages(List<AllMessagesClass> allMessagesClasses) {
        view.onSuccessLoadMessages(allMessagesClasses);
    }

    @Override
    public void onFinished(List<ChatsItemClass> chatsItemClass) {
        view.onSuccessLoad(chatsItemClass);
    }

    @Override
    public void onFailure(String s) {
        view.onFailure();
    }
}

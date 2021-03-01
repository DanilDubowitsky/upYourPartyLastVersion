package com.studenttomsk.upYourParty.Views.Fragments.Messages;

import com.studenttomsk.upYourParty.Classes.AllMessagesClass;
import com.studenttomsk.upYourParty.Classes.ChatsItemClass;
import com.studenttomsk.upYourParty.Classes.DidClass;

import java.util.List;

public interface MessagesMethods {
    interface MessagesView{
        void onSuccessLoadMessages(List<AllMessagesClass> allMessagesClasses);
        void onSuccessLoad(List<ChatsItemClass> chatsItemClass);
        void onSuccessEmail(String email);
        void onFailure();

        void onSuccessReadMessage();
    }
    interface MessagesPresenter{
        void postMyMessages();
        void postMyEmail();
        void redMes(DidClass did);

        void getMessages(DidClass did);
    }
    interface MessagesModel {
        interface OnFinishedListener {
            void onFinishedEmail(String email);
            void onFinishedReading();

            void onFinishedLoadMessages(List<AllMessagesClass> allMessagesClasses);
            void onFinished(List<ChatsItemClass> chatsItemClass);
            void onFailure(String s);
        }

        void getAllMesages(OnFinishedListener onFinishedListener, DidClass did);
        void getMyMessages(OnFinishedListener onFinishedListener, String token);
        void getMyEmail(OnFinishedListener onFinishedListener, String token);
        void readMessage(OnFinishedListener onFinishedListener, DidClass did);
    }

}

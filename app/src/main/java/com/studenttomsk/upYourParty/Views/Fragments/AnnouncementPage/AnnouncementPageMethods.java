package com.studenttomsk.upYourParty.Views.Fragments.AnnouncementPage;

import com.studenttomsk.upYourParty.Classes.DidClass;
import com.studenttomsk.upYourParty.Classes.RoomChatDto;

public interface AnnouncementPageMethods {

    interface AnnouncePageView{
        void onSuccessDid(DidClass didClass);
        void onSuccess();
    }
    interface AnnouncePagePresentser{
        void presenterCreateRoom(RoomChatDto roomChatDto, String token);
    }
    interface AnnouncePageModel{
        interface OnFinishedListener{
            void onFinishCreate(DidClass didClass);
            void onFinishFav();
            void onFailure(String msg);
        }
        void createRoom(OnFinishedListener onF, RoomChatDto roomChatDto, String token);

    }

}

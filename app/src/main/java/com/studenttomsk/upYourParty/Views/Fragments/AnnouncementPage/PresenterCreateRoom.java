package com.studenttomsk.upYourParty.Views.Fragments.AnnouncementPage;

import com.studenttomsk.upYourParty.Classes.DidClass;
import com.studenttomsk.upYourParty.Classes.RoomChatDto;

public class PresenterCreateRoom implements AnnouncementPageMethods.AnnouncePagePresentser, AnnouncementPageMethods.AnnouncePageModel.OnFinishedListener {
    AnnouncementPageMethods.AnnouncePageView view;
    AnnouncementPageMethods.AnnouncePageModel model;

    public PresenterCreateRoom(AnnouncementPageMethods.AnnouncePageView view, AnnouncementPageMethods.AnnouncePageModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void presenterCreateRoom(RoomChatDto roomChatDto, String token) {
        model.createRoom(this,roomChatDto,token);
    }

    @Override
    public void onFinishCreate(DidClass didClass) {
        view.onSuccessDid(didClass);
    }

    @Override
    public void onFinishFav() {
        view.onSuccess();
    }

    @Override
    public void onFailure(String msg) {
    }
}

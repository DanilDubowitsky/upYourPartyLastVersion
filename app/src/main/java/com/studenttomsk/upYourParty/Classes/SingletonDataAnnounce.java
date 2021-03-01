package com.studenttomsk.upYourParty.Classes;

public class SingletonDataAnnounce {
    private static SingletonDataAnnounce instance;
    private AnnounceClass announceClass;

    public void setData(AnnounceClass announceClass){
        this.announceClass = announceClass;
    }
    public AnnounceClass getData(){
        return announceClass;
    }
    public static synchronized SingletonDataAnnounce getInstance() {
        if(instance == null){
            instance = new SingletonDataAnnounce();
        }
        return instance;
    }
}

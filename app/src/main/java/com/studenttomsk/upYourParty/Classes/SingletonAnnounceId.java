package com.studenttomsk.upYourParty.Classes;

public class SingletonAnnounceId {

    private static SingletonAnnounceId instance;
    private Long id;
    public void setId(long id)
    {
        this.id = id;
    }
    public long getId()
    {
        return id;
    }
    public static synchronized SingletonAnnounceId getInstance(){
        if(instance == null){
            instance = new SingletonAnnounceId();
        }
        return instance;
    }
}

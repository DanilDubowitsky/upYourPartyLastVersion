package com.studenttomsk.upYourParty.Classes;

public class SingletonImageProfileSocial {

    private static SingletonImageProfileSocial instance;
    private String img;

    public void setDid(String did)
    {
        this.img = did;
    }
    public String getDid(){
        return img;
    }
    public static synchronized SingletonImageProfileSocial getInstance(){
        if(instance == null){
            instance = new SingletonImageProfileSocial();
        }
        return instance;
    }
}

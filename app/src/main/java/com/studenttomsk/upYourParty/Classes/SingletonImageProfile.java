package com.studenttomsk.upYourParty.Classes;

public class SingletonImageProfile {
    private static SingletonImageProfile instance;
    private String img;

    public void setDid(String did){
        this.img = did;
    }
    public String getDid(){
        return img;
    }
    public static synchronized SingletonImageProfile getInstance(){
        if(instance == null){
            instance = new SingletonImageProfile();
        }
        return instance;
    }

}

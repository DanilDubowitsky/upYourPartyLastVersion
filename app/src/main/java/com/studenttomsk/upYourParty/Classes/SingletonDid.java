package com.studenttomsk.upYourParty.Classes;

public class SingletonDid {

    private static SingletonDid instance;
    private String did;

    public void setDid(String did){
        this.did = did;
    }
    public String getDid(){
        return did;
    }
    public static synchronized SingletonDid getInstance(){
        if(instance == null){
            instance = new SingletonDid();
        }
        return instance;
    }
}

package com.studenttomsk.upYourParty.Classes;

public class SingletonCity {
    private static SingletonCity instance;
    private String token;
    public void setCity(String token){
        this.token = token;
    }
    public String getCity(){
        return token;
    }
    public static synchronized SingletonCity getInstance(){
        if(instance == null){
            instance = new SingletonCity();
        }
        return instance;
    }
}

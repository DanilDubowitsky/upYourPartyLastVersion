package com.studenttomsk.upYourParty.Classes;

public class SingletonEmail {
    private static SingletonEmail instance;
    private String email;
    public void clearEmail(){
        email = "";
    }
    public void setEmail(String token){
        this.email = token;
    }
    public String getEmail(){
        return email;
    }
    public static synchronized SingletonEmail getInstance(){
        if(instance == null){
            instance = new SingletonEmail();
        }
        return instance;
    }
}

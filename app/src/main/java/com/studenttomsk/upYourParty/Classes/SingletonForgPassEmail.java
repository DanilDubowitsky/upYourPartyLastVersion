package com.studenttomsk.upYourParty.Classes;

public class SingletonForgPassEmail {
    private static SingletonForgPassEmail instance;
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
    public static synchronized SingletonForgPassEmail getInstance(){
        if(instance == null){
            instance = new SingletonForgPassEmail();
        }
        return instance;
    }
}

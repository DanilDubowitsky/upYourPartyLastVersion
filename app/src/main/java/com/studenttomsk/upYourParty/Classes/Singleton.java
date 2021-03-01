package com.studenttomsk.upYourParty.Classes;

public class Singleton {
    private static Singleton instance;
    private String token;
    private boolean guest;
    private boolean socialEnter;
    public boolean getSocialEnter(){
        return socialEnter;
    }
    public void setSocialEnter(boolean enter){
        socialEnter = enter;
    }

    public boolean isGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

    public void clearSingleton(){
        token = null;
    }
    public void SetToken(String token){
        this.token = token;
    }
    public String GetToken(){
        return token;
    }
    public static synchronized Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }


}

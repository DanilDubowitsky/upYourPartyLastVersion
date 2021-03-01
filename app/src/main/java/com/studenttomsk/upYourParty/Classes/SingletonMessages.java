package com.studenttomsk.upYourParty.Classes;

import java.util.List;

public class SingletonMessages {
    private static SingletonMessages instance;
    private List<AllMessagesClass> results;
    public void SetData(List<AllMessagesClass> results){
        this.results = results;
    }
    public List<AllMessagesClass> GetData(){
        return results;
    }
    public static synchronized SingletonMessages getInstance(){
        if(instance == null){
            instance = new SingletonMessages();
        }
        return instance;
    }

}

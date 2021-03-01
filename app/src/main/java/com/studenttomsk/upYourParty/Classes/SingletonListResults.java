package com.studenttomsk.upYourParty.Classes;

import java.util.ArrayList;

public class SingletonListResults {
    private static SingletonListResults instance;
    private ArrayList<SearchAnnouncementRes> results;
    public void SetData(ArrayList<SearchAnnouncementRes> results){
        this.results = results;
    }
    public ArrayList<SearchAnnouncementRes> GetData(){
        return results;
    }
    public static synchronized SingletonListResults getInstance(){
        if(instance == null){
            instance = new SingletonListResults();
        }
        return instance;
    }
}

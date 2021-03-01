package com.studenttomsk.upYourParty.Classes;

import java.util.List;

public class SingletonListImagesNames {

    private static SingletonListImagesNames instance;
    private List<String> results;
    public void SetData(List<String> results){
        this.results = results;
    }
    public void clearSingleton(){
    try {
        if(results.size()>0) {
          results.clear();
        }
    }
    catch (Exception e) {

    }

    }
    public List<String> GetData(){
        return results;
    }
    public static synchronized SingletonListImagesNames getInstance(){
        if(instance == null){
            instance = new SingletonListImagesNames();
        }
        return instance;
    }

}

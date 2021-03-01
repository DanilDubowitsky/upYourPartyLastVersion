package com.studenttomsk.upYourParty.Classes;

public class SingletonCategory {
    private static SingletonCategory  instance;
    private String category;

    public static void setInstance(SingletonCategory instance) {
        SingletonCategory.instance = instance;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static synchronized SingletonCategory  getInstance(){
        if(instance == null){
            instance = new SingletonCategory();
        }
        return instance;
    }
}

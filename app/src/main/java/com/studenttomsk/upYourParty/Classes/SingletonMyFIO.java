package com.studenttomsk.upYourParty.Classes;

public class SingletonMyFIO {
    private static SingletonMyFIO instance;
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static synchronized SingletonMyFIO getInstance(){
        if(instance == null){
            instance = new SingletonMyFIO();
        }
        return instance;
    }
}

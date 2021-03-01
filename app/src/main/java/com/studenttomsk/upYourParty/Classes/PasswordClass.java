package com.studenttomsk.upYourParty.Classes;

public class PasswordClass {
    private String oldPassword;
    private String password;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PasswordClass(String oldPassword, String password) {
        this.oldPassword = oldPassword;
        this.password = password;
    }
}

package com.studenttomsk.upYourParty.Classes;

import com.google.gson.Gson;

public class ChatMessageClass {
    private String myEmail;
    private String authPerson_to;
    private String message;
    private String time;
    private String did;
    private String imageMessage;

    public String getImageMessage() {
        return imageMessage;
    }

    public void setImageMessage(String imageMessage) {
        this.imageMessage = imageMessage;
    }

    public ChatMessageClass(String myEmail, String authPerson_to, String message, String time, String did, String imageMessage) {
        this.myEmail = myEmail;
        this.authPerson_to = authPerson_to;
        this.message = message;
        this.time = time;
        this.did = did;
        this.imageMessage = imageMessage;
    }

    public String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
    }

    public String getAuthPerson_to() {
        return authPerson_to;
    }

    public void setAuthPerson_to(String authPerson_to) {
        this.authPerson_to = authPerson_to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String json() {
        return new Gson().toJson(this);
    }




}

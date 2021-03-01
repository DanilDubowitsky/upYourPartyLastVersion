package com.studenttomsk.upYourParty.Classes;

import com.studenttomsk.upYourParty.Classes.ProfilePersonFrom;

public class AllMessagesClass {
    private ProfilePersonFrom authPerson_from;
    private String authPerson_to;
    private String time;
    private String did;
    private boolean readMessage;
    private String message;
    private String fio;
    private String imageMessage;

    public String getImageMessage() {
        return imageMessage;
    }

    public void setImageMessage(String imageMessage) {
        this.imageMessage = imageMessage;
    }

    public ProfilePersonFrom getAuthPerson_from() {
        return authPerson_from;
    }

    public void setAuthPerson_from(ProfilePersonFrom authPerson_from) {
        this.authPerson_from = authPerson_from;
    }

    public boolean isReadMessage() {
        return readMessage;
    }

    public String getAuthPerson_to() {
        return authPerson_to;
    }

    public void setAuthPerson_to(String authPerson_to) {
        this.authPerson_to = authPerson_to;
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

    public boolean getReadMessage() {
        return readMessage;
    }

    public void setReadMessage(boolean readMessage) {
        this.readMessage = readMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public AllMessagesClass(ProfilePersonFrom authPerson_from, String authPerson_to, String time, String did, boolean readMessage, String message, String fio, String imageMessage) {
        this.authPerson_from = authPerson_from;
        this.authPerson_to = authPerson_to;
        this.time = time;
        this.did = did;
        this.readMessage = readMessage;
        this.message = message;
        this.fio = fio;
        this.imageMessage = imageMessage;
    }
}

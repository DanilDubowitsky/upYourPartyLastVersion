package com.studenttomsk.upYourParty.Classes;

public class ChatsItemClass {
    private String fio;
    private String time;
    private String lastMessage;
    private boolean readMessage;
    private String did;
    private String lastEmail;
    private String myEmail;
    private String email_to;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail_to() {
        return email_to;
    }

    public void setEmail_to(String email_to) {
        this.email_to = email_to;
    }

    public String getLastEmail() {
        return lastEmail;
    }

    public void setLastEmail(String lastEmail) {
        this.lastEmail = lastEmail;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public boolean isReadMessage() {
        return readMessage;
    }

    public void setReadMessage(boolean readMessage) {
        this.readMessage = readMessage;
    }

    public String getFio() {
        return fio;
    }



    public String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
    }


    public ChatsItemClass(String fio, String time, String lastMessage, boolean readMessage, String myEmail) {
        this.fio = fio;
        this.time = time;
        this.lastMessage = lastMessage;
        this.readMessage = readMessage;
        this.myEmail = myEmail;
    }
}

package com.example.revobanga.Chat;

public class Message {
    public static final int SENT = 0;//ct
    public static final int RECEIVED = 1;//et
    private String userID;

    public String sender;
    public String message;
    private Long createdAt;
    private int mType;

    public Message(){ }
    public Message(String sender,String message, Long createdAt,int mType){
        this.sender=sender;
        this.message=message;
        this.createdAt=createdAt;
        this.mType=mType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}

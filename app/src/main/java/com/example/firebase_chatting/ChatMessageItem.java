package com.example.firebase_chatting;

public class ChatMessageItem {
    private String name;
    private String contents;
    private String notice;
    private String messageType;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getMessageType() {
        return messageType;
    }
    public void setMessageType(String messageType){
        this.messageType = messageType;
    }
}

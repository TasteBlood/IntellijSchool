package com.cloudcreativity.intellijSchool.entity;

public class CommonEntity {
    private String commentContent;
    private int fromId;
    private String fromUserName;
    private int toId;
    private String toUserName;

    public String getCommonContent() {
        return commentContent;
    }

    public void setCommonContent(String commonContent) {
        this.commentContent = commonContent;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
}

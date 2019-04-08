package com.cloudcreativity.intellijSchool.entity;

import java.util.List;

public class FootEntity {
    private int pid;
    private int uid;
    private String content;
    private String picture;
    private String video;
    private int zanNum;
    private String createTime;
    private UserEntity userDomain;
    private List<CommonEntity> proCommentDomainList;


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public List<CommonEntity> getProCommentDomainList() {
        return proCommentDomainList;
    }

    public void setProCommentDomainList(List<CommonEntity> proCommentDomainList) {
        this.proCommentDomainList = proCommentDomainList;
    }

    public int getZanNum() {
        return zanNum;
    }

    public void setZanNum(int zanNum) {
        this.zanNum = zanNum;
    }

    public UserEntity getUserDomain() {
        return userDomain;
    }

    public void setUserDomain(UserEntity userDomain) {
        this.userDomain = userDomain;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}

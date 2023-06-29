package com.fifatoy.model;

public class membersaveRequest {
    private String id;
    private String nickname;
    private String pwd;

    public membersaveRequest(String id, String nickname, String pwd) {
        this.id = id;
        this.nickname = nickname;
        this.pwd = pwd;
    }

    public String getid() {
        return id;
    }

    public String getnickname() {
        return nickname;
    }

    public String getpwd() {
        return pwd;
    }

}

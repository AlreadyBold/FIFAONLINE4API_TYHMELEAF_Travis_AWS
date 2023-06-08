package com.fifatoy.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity

@Table(name = "user_info")
public class UserInfo {
    @Column(name = "user_no")
    private int userNo;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_pw")
    private String userPw;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_joindate")
    private Date userJoindate;
    @Column(name = "user_fifaid")
    private String userFifaid;
    @Column(name = "user_birth")
    private Date userBirth;
    @Column(name = "user_grade")
    private int userGrade;
    @Column(name = "user_delete_yn")
    private int userDeleteYn;

    public int getUserDeleteYn() {
        return userDeleteYn;
    }

    public void setUserDeleteYn(int userDeleteYn) {
        this.userDeleteYn = userDeleteYn;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getUserJoindate() {
        return userJoindate;
    }

    public void setUserJoindate(Date userJoindate) {
        this.userJoindate = userJoindate;
    }

    public String getUserFifaid() {
        return userFifaid;
    }

    public void setUserFifaid(String userFifaid) {
        this.userFifaid = userFifaid;
    }

    public Date getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(Date userBirth) {
        this.userBirth = userBirth;
    }

    public int getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(int userGrade) {
        this.userGrade = userGrade;
    }

}

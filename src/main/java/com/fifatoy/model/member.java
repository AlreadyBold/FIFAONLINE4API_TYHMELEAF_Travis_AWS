package com.fifatoy.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "member")
@Data
public class member {
    @Id
    private String id;
    private String pwd;
    private String nickname;
    @Column(name = "create_date")
    private LocalDateTime createDate;

    protected member() {
    }

    public member(String id, String pwd, String nickname, LocalDateTime createDate) {
        this.id = id;
        this.pwd = pwd;
        this.nickname = nickname;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public String getnickname() {
        return nickname;
    }

    public String getpwd() {
        return pwd;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

}

package com.fifatoy.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fifatoy.model.UserInfo;
import com.fifatoy.repository.jpaloginrepo;
import com.fifatoy.service.jpaloginservice;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class jpaloginserviceImpl implements jpaloginservice {

    @Autowired
    jpaloginrepo jpaloginrep;

    @Override
    public List<UserInfo> findByUserId(String userId, String userPw) {
        System.out.println(userId);
        return jpaloginrep.findByUserId(userId, userPw);
    }

}

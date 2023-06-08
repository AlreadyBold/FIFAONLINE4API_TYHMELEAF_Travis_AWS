package com.fifatoy.service;

import java.util.List;
import com.fifatoy.model.UserInfo;

public interface jpaloginservice {

    public List<UserInfo> findByUserId(String userId, String userPw);

}

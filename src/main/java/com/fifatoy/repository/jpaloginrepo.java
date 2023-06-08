package com.fifatoy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fifatoy.model.UserInfo;

@Repository
public interface jpaloginrepo extends CrudRepository<UserInfo, Object> {
    @Query(nativeQuery = true, value = "select * from user_info where user_id = :userId and user_pw = :userPw")
    List<UserInfo> findByUserId(
            @Param("userId") String userId, @Param("userPw") String userPw);

}

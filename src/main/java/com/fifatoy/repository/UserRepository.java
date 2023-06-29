package com.fifatoy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.fifatoy.model.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findById(String email);

    @Transactional
    @Modifying
    @Query(value = " update jpabegin.user set name= :name where email= :email ;", nativeQuery = true)
    void update(@Param("email") String email, @Param("name") String name) throws Exception;

    // void save(User user);

    void delete(User user);
}

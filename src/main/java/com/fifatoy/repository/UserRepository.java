package com.fifatoy.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.fifatoy.model.User;

public interface UserRepository extends Repository<User, String> {

    Optional<User> findById(String email);

    void save(User user);

    void delete(User user);
}

package com.fifatoy.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface getUserRepository extends Repository<User, String> {
    Optional<User> findById(String email);

    void save(User user);

    void delete(User user);
}

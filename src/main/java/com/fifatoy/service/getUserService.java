package com.fifatoy.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fifatoy.exception.NoUserException;
import com.fifatoy.model.User;
import com.fifatoy.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class getUserService {

    // JPA REPOSITORY FINAL 필수 .. . .
    final UserRepository getuserrepository;

    public User getUser(String email) {
        Optional<User> userOpt = getuserrepository.findById(email);
        return userOpt.orElseThrow(() -> new NoUserException());
    }
}

package com.fifatoy.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fifatoy.exception.NoUserException;
import com.fifatoy.model.User;
import com.fifatoy.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class deleteUserService {
    final UserRepository userrepository;

    @Transactional
    public void deleteUser(String email) {
        Optional<User> userOpt = userrepository.findById(email);
        User user = userOpt.orElseThrow(() -> new NoUserException());
        userrepository.delete(user);
    }
}

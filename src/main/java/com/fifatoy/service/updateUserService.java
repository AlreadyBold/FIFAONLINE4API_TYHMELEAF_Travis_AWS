package com.fifatoy.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fifatoy.exception.DupException;
import com.fifatoy.model.SaveRequest;
import com.fifatoy.model.User;
import com.fifatoy.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class updateUserService {

    final UserRepository userrepository;

    @Transactional
    public void updateUser(SaveRequest SaveRequest) {
        Optional<User> userOpt = userrepository.findById(SaveRequest.getEmail());
        if (userOpt.isPresent()) {
            User newUser = new User(SaveRequest.getEmail(), "UPDATE", LocalDateTime.now());
            userrepository.save(newUser);
        } else {
            throw new DupException();
        }

    }
}

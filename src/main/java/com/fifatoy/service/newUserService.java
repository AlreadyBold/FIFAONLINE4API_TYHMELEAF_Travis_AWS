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
public class newUserService {

    final UserRepository userrepository;

    public void saveUser(SaveRequest SaveRequest) {
        Optional<User> userOpt = userrepository.findById(SaveRequest.getEmail());
        if (userOpt.isPresent())
            throw new DupException();
        User newUser = new User(SaveRequest.getEmail(), SaveRequest.getName(), LocalDateTime.now());
        userrepository.save(newUser);
    }
}

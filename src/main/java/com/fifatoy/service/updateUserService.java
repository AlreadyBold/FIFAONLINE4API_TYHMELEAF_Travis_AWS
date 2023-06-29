package com.fifatoy.service;

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
    public void updateUser(SaveRequest SaveRequest) throws Exception {
        Optional<User> userOpt = userrepository.findById(SaveRequest.getEmail());
        if (userOpt.isPresent()) {
            userrepository.update(SaveRequest.getEmail(), "nativeQuery");
        } else {
            throw new DupException();
        }

    }
}

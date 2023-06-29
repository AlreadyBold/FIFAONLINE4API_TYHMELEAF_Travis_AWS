package com.fifatoy.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fifatoy.exception.NoUserException;

import com.fifatoy.model.member;
import com.fifatoy.repository.memberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class getMemberService {

    final memberRepository memberrepository;

    public member getUser(String email) {
        Optional<member> memberOpt = memberrepository.findById(email);
        return memberOpt.orElseThrow(() -> new NoUserException());
    }
}

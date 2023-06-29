package com.fifatoy.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fifatoy.model.member;
import com.fifatoy.model.membersaveRequest;
import com.fifatoy.repository.memberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class newMemberService {

    final memberRepository memberrepository;

    @Transactional
    public void saveMember(membersaveRequest membersaveRequest) {
        member newmember = new member(membersaveRequest.getid(), membersaveRequest.getnickname(),
                membersaveRequest.getpwd(),
                LocalDateTime.now());
        memberrepository.save(newmember);
    }

}

package com.fifatoy.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.fifatoy.model.member;

public interface memberRepository extends Repository<member, String> {

    Optional<member> findById(String id);

    void save(member member);

}

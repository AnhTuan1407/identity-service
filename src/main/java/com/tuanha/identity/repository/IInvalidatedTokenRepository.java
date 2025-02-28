package com.tuanha.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuanha.identity.model.InvalidatedToken;

public interface IInvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    
}

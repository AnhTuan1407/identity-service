package com.tuanha.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tuanha.identity.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, String>{
    
}

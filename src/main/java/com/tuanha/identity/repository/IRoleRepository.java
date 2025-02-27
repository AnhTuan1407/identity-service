package com.tuanha.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tuanha.identity.model.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, String> {
    
}

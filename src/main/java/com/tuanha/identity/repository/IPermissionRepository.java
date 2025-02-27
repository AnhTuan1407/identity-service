package com.tuanha.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tuanha.identity.model.Permission;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, String>{
    
}

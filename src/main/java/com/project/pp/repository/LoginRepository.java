package com.project.pp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.pp.model.Login;

public interface LoginRepository extends JpaRepository<Login,Long> {
    Optional<Login> findByUsername(String username);
}

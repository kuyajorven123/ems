package com.project.ems.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
    List<User> findByStatusAndRole(String status, String role);
    // List<User> findByRole(String role);
}

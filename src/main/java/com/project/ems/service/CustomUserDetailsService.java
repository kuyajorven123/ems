package com.project.ems.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.ems.model.User;
import com.project.ems.model.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
     private final UserRepository employeeRepository;

    public CustomUserDetailsService(UserRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User employee = employeeRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(employee.getEmail())
                .password(employee.getPassword())
                .roles(employee.getRole())
                .build();
    }
}

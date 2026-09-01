package com.project.ems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final AuthFailureHandler authFailureHandler;

        public SecurityConfig(AuthFailureHandler authFailureHandler) {
        this.authFailureHandler = authFailureHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/adminlte/**").permitAll()
                        .requestMatchers("/active_employee").hasRole("ADMIN")
                        .requestMatchers("/inactive_employee").hasRole("ADMIN")
                        .requestMatchers("/apply_leave").hasAnyRole("ADMIN","EMPLOYEE")
                        .requestMatchers("/leave_application").hasRole("ADMIN")
                        .requestMatchers("/","/dashboard").hasAnyRole("ADMIN","EMPLOYEE")
                        .requestMatchers("/settings").hasAnyRole("ADMIN","EMPLOYEE")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                        .failureHandler(authFailureHandler)
                        .permitAll())
                        
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access_denied"))

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }

}
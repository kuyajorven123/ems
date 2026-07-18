package com.project.ems.model;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentsRepository extends JpaRepository<Departments, Long> {
    Optional<Departments> findByDepartment(String department);
}

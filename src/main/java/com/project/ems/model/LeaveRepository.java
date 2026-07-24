package com.project.ems.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long>{
    List<Leave> findByUserId(User user);

}

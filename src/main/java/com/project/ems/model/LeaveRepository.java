package com.project.ems.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    List<Leave> findByUser(User user);

    @Query("""
        SELECT l
        FROM Leave l
        ORDER BY
            CASE
                WHEN l.status = 'Pending' THEN 0
                WHEN l.status = 'Approved' THEN 1
                WHEN l.status = 'Rejected' THEN 2
                ELSE 3
            END,
            l.dateApplied DESC
    """)
    List<Leave> findAllForApplications();

    List<Leave> findAll();

}

package com.disdik.promote.repository;

import com.disdik.promote.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Employee entity.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}

package com.emp.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.emp.api.model.Employee;

@Repository
public interface EmployeeRepository extends  JpaRepository<Employee, Integer>{

	@Query("select e from Employee e where e.dept =:dept")
	List<Employee> getEmployeeByDept(@Param("dept") String dept);

}

package com.emp.api.service;

import java.util.List;

import com.emp.api.model.Employee;

public interface EmployeeService {

	Integer saveEmp(Employee employee);

	Employee getEmployeeById(Integer empId);

	void updateEmp(Employee employee);

	List<Employee> getEmployees();

	void deleteAll();

	void deleteById(Integer empId);

	List<Employee> getEmployeeByDept(String dept);


}

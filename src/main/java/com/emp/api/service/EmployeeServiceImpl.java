package com.emp.api.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emp.api.model.Employee;
import com.emp.api.repository.EmployeeRepository;

@Service
@Transactional 
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public Integer saveEmp(Employee employee) {
		Employee emp = employeeRepository.save(employee);;
		return emp.getEmpId();
	}

	@Override
	public Employee getEmployeeById(Integer empId) {
		return employeeRepository.findById(empId).orElse(null);
	}

	@Override
	public void updateEmp(Employee employee) {
		employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getEmployees() {
		return (List<Employee>) employeeRepository.findAll();
	}

	@Override
	public void deleteAll() {
		employeeRepository.deleteAll();		
	}

	@Override
	public void deleteById(Integer empId) {
		employeeRepository.deleteById(empId);
	}

	@Override
	public List<Employee> getEmployeeByDept(String dept) {
		return employeeRepository.getEmployeeByDept(dept);
	}

}

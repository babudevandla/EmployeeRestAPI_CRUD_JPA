package com.emp.api.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emp.api.model.Employee;
import com.emp.api.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

@RestController
@RequestMapping("/api/v1")
public class EmployeeRestController {

	@Autowired
	EmployeeService employeeService;

	@PostMapping("/employee")
	public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) {
		Employee emp = employeeService.getEmployeeById(employee.getEmpId());
		if (emp != null) {
			return new ResponseEntity<String>("BAD REQUEST", HttpStatus.BAD_REQUEST);
		}
		Integer id = employeeService.saveEmp(employee);
		if (id != null) {
			return new ResponseEntity<String>("CREATED", HttpStatus.CREATED);
		}
		return null;
	}

	@PutMapping("/employee")
	public ResponseEntity<?> updateEmp(@RequestBody Employee employee) {

		Employee emp = employeeService.getEmployeeById(employee.getEmpId());
		if (emp != null) {
			employeeService.updateEmp(employee);
			return new ResponseEntity<String>("Updated", HttpStatus.OK);
		}
		return new ResponseEntity<String>("BAD REQUEST", HttpStatus.BAD_REQUEST);
	}

	@PatchMapping(path = "/employee/{empId}")
	public ResponseEntity<?> partialUpdate(@PathVariable Integer empId, @RequestParam String name,
			@RequestParam String dept) {
		Employee emp = employeeService.getEmployeeById(empId);
		if (emp != null) {
			emp.setName(name);
			emp.setDept(dept);
			employeeService.updateEmp(emp);
			return new ResponseEntity<String>("Updated", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Not found!", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/employees")
	public ResponseEntity<?> getEmployees() {
		List<Employee> emplList = employeeService.getEmployees();
		return new ResponseEntity<List<Employee>>(emplList, HttpStatus.OK);
	}

	@GetMapping("/emp/{empId}") // /api/empoyee/101
	public ResponseEntity<?> getEmployeeById(@PathVariable(name = "empId") Integer empId) {

		Employee emp = employeeService.getEmployeeById(empId);
		if (emp != null) {
			return new ResponseEntity<Employee>(emp, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Not found!", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/employee/{dept}") // /api/empoyee/101
	public ResponseEntity<?> getEmployeeById(@PathVariable(name = "dept") String dept) {

		List<Employee> emp = employeeService.getEmployeeByDept(dept);
		if (emp != null) {
			return new ResponseEntity<List<Employee>>(emp, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Not found!", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/emp") // /api/emp?empId=
	public ResponseEntity<?> getEmpId(@RequestParam Integer empId) {
		if (empId != null) {
			Employee emp = employeeService.getEmployeeById(empId);
			if (emp != null) {
				return new ResponseEntity<Employee>(emp, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Not found!", HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>("BAD REQUEST", HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/employee")
	public ResponseEntity<?> deleteAll() {
		employeeService.deleteAll();
		return new ResponseEntity<String>("Deleted All Employees", HttpStatus.OK);
	}

	@DeleteMapping("/employee/{empId}")
	public ResponseEntity<?> deleteById(@PathVariable Integer empId) {
		Employee emp = employeeService.getEmployeeById(empId);
		if (emp != null) {
			employeeService.deleteById(empId);
			return new ResponseEntity<String>("Deleted By empId", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Not found!", HttpStatus.NOT_FOUND);
		}
	}

	private Employee applyPatchToCustomer(JsonPatch patch, Employee targetEmployee)
			throws JsonPatchException, JsonProcessingException {
		ObjectMapper objectMapper =  new ObjectMapper();
		JsonNode patched = patch.apply(objectMapper.convertValue(targetEmployee, JsonNode.class));
		return objectMapper.treeToValue(patched, Employee.class);
	}
}

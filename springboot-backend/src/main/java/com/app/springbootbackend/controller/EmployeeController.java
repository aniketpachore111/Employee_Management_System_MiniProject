package com.app.springbootbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.springbootbackend.exception.ResourceNotFoundException;
import com.app.springbootbackend.model.Employee;
import com.app.springbootbackend.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees()
	{
		return employeeRepository.findAll();
		
	}
	
	//create employee rest api
	@PostMapping("/employees")
	public Employee CreateEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	//Get employee by ID
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
		Employee employee =  employeeRepository.findById(id)
												.orElseThrow(() -> new ResourceNotFoundException("Employee Not Exists with Id : "+id));
		return ResponseEntity.ok(employee);
	}
	
	//Update Employee rest API
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeeDetails)
	{
		Employee employee =  employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not Exists with Id : "+id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable long id){
		Employee employee =  employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not Exists with Id : "+id));
		
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
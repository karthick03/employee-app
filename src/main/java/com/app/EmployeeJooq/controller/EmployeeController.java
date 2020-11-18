package com.app.EmployeeJooq.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.EmployeeJooq.service.EmployeeService;
import com.employee.jooq.tables.pojos.EmployeeInfo;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);
	private final EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	@PostMapping("/add")
	public ResponseEntity<String> addEmployeeDetails(@RequestBody EmployeeInfo employeeInfo) {
		LOG.info("[Controller] Request received for /employee/add");
		return employeeService.addEmployee(employeeInfo);
	}

	@GetMapping("/get")
	public List<EmployeeInfo> getAllEmployeeDetails() {
		LOG.info("[Controller] Request received for /employee/get");
		return employeeService.getAllEmployees();
	}

	@GetMapping("/get/{employeeId}")
	public EmployeeInfo getAllEmployeeDetails(@PathVariable String employeeId) {
		LOG.info("[Controller] Request received for /employee/get/{employeeId}");
		return employeeService.getEmployee(employeeId);
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateEmployeeDetails(@RequestBody EmployeeInfo employeeInfo) {
		LOG.info("[Controller] Request received for /employee/update");
		return employeeService.updateEmployee(employeeInfo);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteEmployeeDetails(@RequestBody EmployeeInfo employeeInfo) {
		LOG.info("[Controller] Request received for /employee/delete");
		return employeeService.deleteEmployee(employeeInfo);
	}
}

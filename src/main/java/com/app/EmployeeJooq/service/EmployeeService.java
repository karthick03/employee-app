package com.app.EmployeeJooq.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.EmployeeJooq.repository.EmployeeRepository;
import com.employee.jooq.tables.pojos.EmployeeInfo;

@Service
public class EmployeeService {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);
	private final EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	public ResponseEntity<String> addEmployee(EmployeeInfo employeeInfo) {
		try {
			LOG.info("[Service] Trying to add employee details: {}", employeeInfo);
			EmployeeInfo savedEmployeeInfo = employeeRepository.addEmployee(employeeInfo);
			LOG.info("[Service] Employee details added successfully: {}", savedEmployeeInfo.getEmployeeId());
			return new ResponseEntity<String>(savedEmployeeInfo.getEmployeeId() + " Employee Added Successfully",
					HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("[Service] Error in adding employee details: {}", employeeInfo, e);
			return new ResponseEntity<String>("Failed to add Employee Details", HttpStatus.BAD_REQUEST);
		}
	}

	public List<EmployeeInfo> getAllEmployees() {
		List<EmployeeInfo> employeeList = new ArrayList<>();
		try {
			LOG.info("[Service] Trying to fetch all employee details...");
			employeeList = employeeRepository.getAllEmployees();
			LOG.info("[Service] {} Employee details fetched", employeeList.size());
		} catch (Exception e) {
			LOG.error("[Service] Error in fetching employee details...", e);
		}
		return employeeList;
	}

	public EmployeeInfo getEmployee(String employeeId) {
		EmployeeInfo employee = null;
		try {
			LOG.info("[Service] Trying to get employee details for {}", employeeId);
			employee = employeeRepository.getEmployee(employeeId);
			LOG.info("[Service] Got {} for employeeId: {}", employee, employeeId);
		} catch (Exception e) {
			LOG.error("[Service] Failed to get employee details for {}. Returning empty response.", employeeId, e);
		}
		return employee;
	}

	public ResponseEntity<String> updateEmployee(EmployeeInfo employeeInfo) {
		try {
			LOG.info("[Service] Trying to update employee details: {}", employeeInfo);
			EmployeeInfo updatedEmployeeInfo = employeeRepository.updateEmployee(employeeInfo);
			LOG.info("[Service] Employee details updated successfully: {}", updatedEmployeeInfo.getEmployeeId());
			return new ResponseEntity<String>(updatedEmployeeInfo.getEmployeeId() + " Employee Updated Successfully",
					HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("[Service] Error in updating employee details: {}", employeeInfo, e);
			return new ResponseEntity<String>("Failed to Update Employee Details", HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<String> deleteEmployee(EmployeeInfo employeeInfo) {
		try {
			LOG.info("[Service] Trying to delete employee details: {}", employeeInfo);
			EmployeeInfo deletedEmployeeInfo = employeeRepository.deleteEmployee(employeeInfo);
			return new ResponseEntity<String>(deletedEmployeeInfo.getEmployeeId() + " Employee Deleted Successfully",
					HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("[Service] Error in deleting employee details: {}", employeeInfo, e);
			return new ResponseEntity<String>("Failed to Delete Employee Details", HttpStatus.BAD_REQUEST);
		}
	}

}

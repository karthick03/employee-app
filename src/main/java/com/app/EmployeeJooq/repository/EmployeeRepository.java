package com.app.EmployeeJooq.repository;

import static com.employee.jooq.tables.EmployeeInfo.EMPLOYEE_INFO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.employee.jooq.tables.pojos.EmployeeInfo;
import com.employee.jooq.tables.records.EmployeeInfoRecord;

@Repository
public class EmployeeRepository {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeRepository.class);
	private final DSLContext context;

	/**
	 * Converts the returned Record to POJO for easier mapping in overall
	 * application
	 * 
	 * @param employeeRecord
	 * @return
	 */
	private EmployeeInfo convertRecordToPojo(EmployeeInfoRecord employeeRecord) {
		LOG.debug("[Repository] Converting Employee Record to Pojo...");
		EmployeeInfo employeeInfo = new EmployeeInfo();
		employeeInfo.setEmployeeId(employeeRecord.getEmployeeId());
		employeeInfo.setFirstName(employeeRecord.getFirstName());
		employeeInfo.setLastName(employeeRecord.getLastName());
		employeeInfo.setEmail(employeeRecord.getEmail());
		employeeInfo.setCreatedOn(employeeRecord.getCreatedOn());
		return employeeInfo;
	}

	@Autowired
	public EmployeeRepository(DSLContext context) {
		super();
		this.context = context;
	}

	/**
	 * Adds the employee information to the DB
	 * 
	 * @param employeeInfo Employee Info
	 * @return
	 */
	public EmployeeInfo addEmployee(EmployeeInfo employeeInfo) {
		LOG.info("[Repository] Adding employee: {}", employeeInfo);
		EmployeeInfoRecord savedEmployee = context.insertInto(EMPLOYEE_INFO)
				.columns(EMPLOYEE_INFO.FIRST_NAME, EMPLOYEE_INFO.LAST_NAME, EMPLOYEE_INFO.EMAIL)
				.values(employeeInfo.getFirstName(), employeeInfo.getLastName(), employeeInfo.getEmail()).returning()
				.fetchOne();
		return convertRecordToPojo(savedEmployee);
	}

	/**
	 * Retrieves all the employee related information
	 * 
	 * @return
	 */
	public List<EmployeeInfo> getAllEmployees() {
		LOG.info("[Repository] Fetching all employees...");
		List<EmployeeInfoRecord> allEmployee = context.selectFrom(EMPLOYEE_INFO).fetchInto(EmployeeInfoRecord.class);
		return allEmployee.stream().map(this::convertRecordToPojo).collect(Collectors.toList());
	}

	/**
	 * Retrieves employee information given employeeId
	 * 
	 * @param employeeId EmployeeId
	 * @return
	 */
	public EmployeeInfo getEmployee(String employeeId) {
		LOG.info("[Repository] Getting employee: {}", employeeId);
		EmployeeInfoRecord employee = context.selectFrom(EMPLOYEE_INFO)
				.where(EMPLOYEE_INFO.EMPLOYEE_ID.eq(UUID.fromString(employeeId))).fetchOne();
		return employee != null ? convertRecordToPojo(employee) : new EmployeeInfo();
	}

	/**
	 * Updates the entire employee information with new set of details provided No
	 * support for partial update
	 * 
	 * @param employeeInfo Employee Info
	 * @return
	 */
	public EmployeeInfo updateEmployee(EmployeeInfo employeeInfo) {
		LOG.info("[Repository] Updating employee: {}", employeeInfo);
		EmployeeInfoRecord updatedEmployee = context.update(EMPLOYEE_INFO)
				.set(EMPLOYEE_INFO.FIRST_NAME, employeeInfo.getFirstName())
				.set(EMPLOYEE_INFO.LAST_NAME, employeeInfo.getLastName())
				.set(EMPLOYEE_INFO.EMAIL, employeeInfo.getEmail())
				.where(EMPLOYEE_INFO.EMPLOYEE_ID.eq(employeeInfo.getEmployeeId())).returning().fetchOne();
		return updatedEmployee != null ? convertRecordToPojo(updatedEmployee) : null;
	}

	/**
	 * Deletes the employee information with the help of employeeId provided
	 * 
	 * @param employeeInfo Employee Info containing at least employeeId
	 * @return
	 */
	public EmployeeInfo deleteEmployee(EmployeeInfo employeeInfo) {
		LOG.info("[Repository] Deleting employee: {}", employeeInfo);
		EmployeeInfoRecord deletedEmployee = context.delete(EMPLOYEE_INFO)
				.where(EMPLOYEE_INFO.EMPLOYEE_ID.eq(employeeInfo.getEmployeeId())).returning().fetchOne();
		return deletedEmployee != null ? convertRecordToPojo(deletedEmployee) : null;
	}
}

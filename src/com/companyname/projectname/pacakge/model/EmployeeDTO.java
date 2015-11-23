package com.companyname.projectname.pacakge.model;

import java.io.Serializable;
import java.util.Date;

import com.companyname.projectname.pacakge.hibernate.persistence.Employee;

public class EmployeeDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7083074345679837347L;
	public EmployeeDTO(Employee employee) {
		isActive = employee.isIsActive();
		employeeId = employee.getEmployeeId();
		department = employee.getDepartment().getDepartmentName();
		departmentId = employee.getDepartment().getDepartmentId();
		annualSalary = employee.getAnnualSalary().doubleValue();
		firstName = employee.getFirstName();
		lastName = employee.getLastName();
		phoneNumber = employee.getPhoneNumber();
		stateCode = employee.getStateCode();
		hireDate = employee.getHireDate();
	}
	public Boolean isActive;
    public int employeeId;
    public String department;
    public int departmentId;
    public double annualSalary;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String stateCode;
    public Date hireDate;
}

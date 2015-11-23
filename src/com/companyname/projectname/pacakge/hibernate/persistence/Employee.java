package com.companyname.projectname.pacakge.hibernate.persistence;

// Generated Dec 26, 2009 1:14:45 AM by Hibernate Tools 3.2.4.GA

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Employee generated by hbm2java
 */

/*
 <hibernate-mapping>
    <class name="com.companyname.projectname.pacakge.hibernate.persistence.Employee" table="employee">
        <id name="employeeId" type="int">
            <column name="employeeId" />
            <generator class="assigned" />
        </id>
        <many-to-one name="department" class="com.companyname.projectname.pacakge.hibernate.persistence.Department" fetch="select">
            <column name="departmentId" not-null="true" />
        </many-to-one>
        <property name="firstName" >
            <column name="firstName" not-null="true" />
        </property>
        <property name="lastName" >
            <column name="lastName" not-null="true" />
        </property>
        <property name="hireDate" type="timestamp">
            <column name="hireDate" length="23" not-null="true" />
        </property>
        <property name="stateCode" >
            <column name="stateCode" not-null="true" />
        </property>
        <property name="phoneNumber" >
            <column name="phoneNumber" not-null="true" />
        </property>
        <property name="annualSalary" type="big_decimal">
            <column name="annualSalary" scale="4" not-null="true" />
        </property>
        <property name="isActive" type="boolean">
            <column name="isActive" not-null="true" />
        </property>
    </class>
</hibernate-mapping>
 */
@Entity
@Table(name="employee")
public class Employee {
	@Id
	@Column(name="employeeId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int employeeId;
	
	@ManyToOne(targetEntity=Department.class)
	@JoinColumn(name="departmentId", nullable=false)
	@Fetch(FetchMode.SELECT)
	private Department department;
	
	@Column(name="firstName", nullable=false)
	private String firstName;
	
	@Column(name="lastName", nullable=false)
	private String lastName;
	
	@Column(name="hireDate", nullable=false, length=23)
	private Date hireDate;
	
	@Column(name="stateCode", nullable=false)
	private String stateCode;
	
	@Column(name="phoneNumber", nullable=false)
	private String phoneNumber;
	
	@Column(name="annualSalary", scale=4, nullable=false)
	private BigDecimal annualSalary;
	
	@Column(name="isActive", nullable=false)
	private boolean isActive;

	public Employee() {
	}

	public Employee(int employeeId, Department department,
			String firstName, String lastName, Date hireDate,
			String stateCode, String phoneNumber,
			BigDecimal annualSalary, boolean isActive) {
		this.employeeId = employeeId;
		this.department = department;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hireDate = hireDate;
		this.stateCode = stateCode;
		this.phoneNumber = phoneNumber;
		this.annualSalary = annualSalary;
		this.isActive = isActive;
	}

	public int getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getHireDate() {
		return this.hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getStateCode() {
		return this.stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public BigDecimal getAnnualSalary() {
		return this.annualSalary;
	}

	public void setAnnualSalary(BigDecimal annualSalary) {
		this.annualSalary = annualSalary;
	}

	public boolean isIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

}

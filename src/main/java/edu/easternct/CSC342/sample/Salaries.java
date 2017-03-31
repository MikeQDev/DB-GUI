package edu.easternct.CSC342.sample;

import java.math.BigDecimal;
import java.sql.*;
//import java.util.*;
//import java.text.DateFormat;
import java.text.SimpleDateFormat;
	
public class Salaries {
	
	BigDecimal employeeId;
	BigDecimal salary;
	Timestamp startDate;
	Timestamp endDate;	
	
	public Salaries() {
		
	}
	/**
	 * @param BusinessObjectId
	 */
	public Salaries(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}
	public BigDecimal getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}	
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}	
	
	public String toString() {
		return ("Id: " + getEmployeeId() + 
	"\nSalary: " + getSalary() +
	"\nStart date: " + (new SimpleDateFormat("MM/dd/yyyy").format(getStartDate())) +
	"\nEnd date: " + (new SimpleDateFormat("MM/dd/yyyy").format(getEndDate())));
	}	
}

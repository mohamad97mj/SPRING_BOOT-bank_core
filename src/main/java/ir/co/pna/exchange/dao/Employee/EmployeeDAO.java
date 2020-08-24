package ir.co.pna.exchange.dao.Employee;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.Employee;

import java.util.List;


public interface EmployeeDAO {


	public List<Employee> findAll();

	public Employee findById(int theId);

	public int save(Employee theEmployee);

	public void deleteById(int theId);

}

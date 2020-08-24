package ir.co.pna.exchange.service.Employee;

import ir.co.pna.exchange.entity.Employee;

import java.util.List;


public interface EmployeeService {

	public List<Employee> findAll();

	public Employee findById(int theId);

	public int save(Employee theEntity);

	public void deleteById(int theId);

}

package ir.co.pna.exchange.dao.Contract;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.Contract;

import java.util.List;


public interface ContractDAO {


	public List<Contract> findAll();

	public Contract findById(int id);

	public long save(Contract contract);

	public void deleteById(int id);

}

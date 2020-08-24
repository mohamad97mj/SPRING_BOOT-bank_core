package ir.co.pna.exchange.dao.ExternalTransaction;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.ExternalTransaction;

import java.util.List;


public interface ExternalTransactionDAO {


	public List<ExternalTransaction> findAll();

	public ExternalTransaction findById(int id);

	public int save(ExternalTransaction externalTransaction);

	public void deleteById(int id);

}

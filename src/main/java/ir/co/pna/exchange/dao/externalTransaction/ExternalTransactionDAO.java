package ir.co.pna.exchange.dao.externalTransaction;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.ExternalTransaction;

import java.util.List;


public interface ExternalTransactionDAO {


	List<ExternalTransaction> findAll();

	ExternalTransaction findById(int id);

	int save(ExternalTransaction externalTransaction);

}

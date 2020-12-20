package ir.co.pna.exchange.dao.transaction;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.Transaction;

import java.util.List;


public interface TransactionDAO {


	List<Transaction> findAll();

	Transaction findById(int id);

	int save(Transaction transaction);

}

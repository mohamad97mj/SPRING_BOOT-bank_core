package ir.co.pna.exchange.dao.Transaction;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.Transaction;

import java.util.List;


public interface TransactionDAO {


	public List<Transaction> findAll();

	public Transaction findById(int id);

	public int save(Transaction transaction);

	public void deleteById(int id);

}

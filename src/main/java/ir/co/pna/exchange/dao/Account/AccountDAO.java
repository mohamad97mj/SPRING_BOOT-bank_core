package ir.co.pna.exchange.dao.Account;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.Account;

import java.util.List;


public interface AccountDAO {


	public List<Account> findAll();

	public Account findById(int id);

	public long save(Account account);

	public void deleteById(int id);

}

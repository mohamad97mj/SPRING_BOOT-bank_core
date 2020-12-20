package ir.co.pna.exchange.dao.account;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.Account;

import java.util.List;


public interface AccountDAO {


	List<Account> findAll();

	Account findById(int id);

	long save(Account account);

}

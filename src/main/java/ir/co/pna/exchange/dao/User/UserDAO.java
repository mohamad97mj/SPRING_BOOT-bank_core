package ir.co.pna.exchange.dao.User;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.User;

import java.util.List;


public interface UserDAO {


	public List<User> findAll();

	public User findById(long id);

	public long save(User user);

	public void deleteById(long id);

}

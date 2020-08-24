package ir.co.pna.exchange.dao.Owner;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.Owner;

import java.util.List;


public interface OwnerDAO {


	public List<Owner> findAll();

	public Owner findById(long id);

	public long save(Owner owner);

	public void deleteById(long id);

}

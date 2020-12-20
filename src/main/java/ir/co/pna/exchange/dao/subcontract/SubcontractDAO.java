package ir.co.pna.exchange.dao.subcontract;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.Subcontract;

import java.util.List;


public interface SubcontractDAO {


	List<Subcontract> findAll();

	Subcontract findById(int id);

	Subcontract save(Subcontract subcontract);

}

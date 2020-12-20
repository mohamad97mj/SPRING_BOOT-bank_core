package ir.co.pna.exchange.dao.normalContract;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.NormalContract;

import java.util.List;


public interface NormalContractDAO {


	List<NormalContract> findAll();

	NormalContract findById(int id);

	NormalContract save(NormalContract normalContract);

}

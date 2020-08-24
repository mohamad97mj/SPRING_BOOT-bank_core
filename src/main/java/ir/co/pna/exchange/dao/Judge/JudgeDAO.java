package ir.co.pna.exchange.dao.Judge;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.Judge;

import java.util.List;


public interface JudgeDAO {


	public List<Judge> findAll();

	public Judge findById(long id);

	public long save(Judge judge);

	public void deleteById(long id);

}

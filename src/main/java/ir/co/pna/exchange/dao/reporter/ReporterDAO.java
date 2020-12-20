package ir.co.pna.exchange.dao.reporter;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.Reporter;

import java.util.List;


public interface ReporterDAO {

	List<Reporter> findAll();

	Reporter findById(String judgeId);

	String save(Reporter judge);

}

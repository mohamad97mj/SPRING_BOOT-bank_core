package ir.co.pna.exchange.dao.judge;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.Judge;
import ir.co.pna.exchange.entity.NormalContract;
import ir.co.pna.exchange.entity.Subcontract;

import java.util.List;


public interface JudgeDAO {


	List<Judge> findAll();

	Judge findById(String judgeId);

	String save(Judge judge);

	List<NormalContract> getJudgedNormalContracts(String judgeId);

	List<NormalContract> getNotJudgedNormalContracts(String judgeId);

    NormalContract getJudgedNormalContract(String judgeId, int normalContractId);

	NormalContract getNotJudgedNormalContract(String judgeId, int normalContractId);

	List<Subcontract> getJudgedNormalContractSubcontracts(String judgeId, int normalContractId);

	List<Subcontract> getNotJudgedNormalContractJudgedSubcontracts(String judgeId, int normalContractId);

	List<Subcontract>  getNotJudgedNormalContractNotJudgedSubcontracts(String judgeId, int normalContractId);

	Subcontract getNormalContractSubcontract(String judgeId, int normalContractId, int subcontractId);
}

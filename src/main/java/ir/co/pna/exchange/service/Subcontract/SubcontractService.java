package ir.co.pna.exchange.service.Subcontract.Contract;

import ir.co.pna.exchange.entity.Contract;

import java.util.List;


public interface ContractService {

	public List<Contract> findAll();

	public Contract findById(int id);

	public long save(Contract contract);

	public void deleteById(int id);

}

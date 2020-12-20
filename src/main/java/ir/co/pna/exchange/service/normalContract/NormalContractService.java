package ir.co.pna.exchange.service.normalContract;

import ir.co.pna.exchange.entity.NormalContract;
import ir.co.pna.exchange.entity.Subcontract;

import java.util.List;
import java.util.Map;


public interface NormalContractService {

	List<NormalContract> findAll();

	NormalContract findById(int id);

	NormalContract save(Map<String, Object> payload);

	NormalContract update(NormalContract theNormalContract, Map<String, Object> payload);


	List<Subcontract> getSubcontracts(int id);

	NormalContract charge(NormalContract normalContract, Map<String, Object> payload);

	NormalContract claim(NormalContract theNormalContract, Map<String, Object> payload);
}

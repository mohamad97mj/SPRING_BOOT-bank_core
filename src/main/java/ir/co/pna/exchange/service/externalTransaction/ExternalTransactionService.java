package ir.co.pna.exchange.service.externalTransaction;

import ir.co.pna.exchange.entity.ExternalTransaction;

import java.util.List;


public interface ExternalTransactionService {

	List<ExternalTransaction> findAll();

	ExternalTransaction findById(int id);

	int save(ExternalTransaction externalTransaction);

}

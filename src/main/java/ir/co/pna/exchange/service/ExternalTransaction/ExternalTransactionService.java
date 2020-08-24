package ir.co.pna.exchange.service.ExternalTransaction;

import ir.co.pna.exchange.entity.Contract;
import ir.co.pna.exchange.entity.ExternalTransaction;

import java.util.List;


public interface ExternalTransactionService {

	public List<ExternalTransaction> findAll();

	public ExternalTransaction findById(int id);

	public int save(ExternalTransaction externalTransaction);

	public void deleteById(int id);

}

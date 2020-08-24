package ir.co.pna.exchange.service.Transaction;

import ir.co.pna.exchange.entity.Owner;
import ir.co.pna.exchange.entity.Transaction;

import java.util.List;


public interface TransactionService {

	public List<Transaction> findAll();

	public Transaction findById(int id);

	public long save(Transaction transaction);

	public void deleteById(int id);

}

package ir.co.pna.exchange.service.transaction;

import ir.co.pna.exchange.entity.Transaction;

import java.util.List;


public interface TransactionService {

	List<Transaction> findAll();

	Transaction findById(int id);

	long save(Transaction transaction);

}

package ir.co.pna.exchange.service.transaction;

import ir.co.pna.exchange.dao.transaction.TransactionDAO;
import ir.co.pna.exchange.entity.Transaction;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TransactionServiceImpl implements TransactionService {

	private TransactionDAO transactionDAO;
	
	@Autowired
	public TransactionServiceImpl(@Qualifier("transactionDAOJpaImpl") TransactionDAO theTransactionDAO) {
		transactionDAO = theTransactionDAO;
	}
	
	@Override
	@Transactional
	public List<Transaction> findAll() {
		return transactionDAO.findAll();
	}

	@Override
	@Transactional
	public Transaction findById(int theId) {
		Transaction theTransaction = transactionDAO.findById(theId);

		if (theTransaction == null) {
			throw new MyEntityNotFoundException("ExternalTransaction id not found - " + theId);
		}
		return theTransaction;
	}

	@Override
	@Transactional
	public long save(Transaction Transaction) {
		return transactionDAO.save(Transaction);
	}

}







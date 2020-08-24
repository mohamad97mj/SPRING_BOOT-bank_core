package ir.co.pna.exchange.service.Transaction;

import ir.co.pna.exchange.dao.Transaction.TransactionDAO;
import ir.co.pna.exchange.entity.Transaction;
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
		return transactionDAO.findById(theId);
	}

	@Override
	@Transactional
	public long save(Transaction Transaction) {
		return transactionDAO.save(Transaction);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		transactionDAO.deleteById(theId);
	}

}







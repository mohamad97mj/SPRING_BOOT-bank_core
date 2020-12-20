package ir.co.pna.exchange.dao.transaction;

import ir.co.pna.exchange.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class TransactionDAOJpaImpl implements TransactionDAO {

	private EntityManager entityManager;

	@Autowired
	public TransactionDAOJpaImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Transaction> findAll() {

		// create a query
		Query theQuery =
				entityManager.createQuery("from Transaction");

		// execute query and get result list
		List<Transaction> transactions = theQuery.getResultList();

		// return the results
		return transactions;
	}

	@Override
	public Transaction findById(int theId) {

		Transaction theTransaction =
				entityManager.find(Transaction.class, theId);
		
		return theTransaction;
	}

	@Override
	public int save(Transaction theTransaction) {

		Transaction dbTransaction = entityManager.merge(theTransaction);
		
		// update with id from db ... so we can get generated id for save/insert
		theTransaction.setId(dbTransaction.getId());

		return theTransaction.getId();
		
	}


}











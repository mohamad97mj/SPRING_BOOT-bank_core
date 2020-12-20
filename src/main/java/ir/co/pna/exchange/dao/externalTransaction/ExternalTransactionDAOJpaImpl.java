package ir.co.pna.exchange.dao.externalTransaction;

import ir.co.pna.exchange.entity.ExternalTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class ExternalTransactionDAOJpaImpl implements ExternalTransactionDAO {

	private EntityManager entityManager;

	@Autowired
	public ExternalTransactionDAOJpaImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<ExternalTransaction> findAll() {

		// create a query
		Query theQuery =
				entityManager.createQuery("from ExternalTransaction");

		// execute query and get result list
		List<ExternalTransaction> externalTransactions = theQuery.getResultList();

		// return the results
		return externalTransactions;
	}

	@Override
	public ExternalTransaction findById(int theId) {

		ExternalTransaction theExternalTransaction =
				entityManager.find(ExternalTransaction.class, theId);
		
		return theExternalTransaction;
	}

	@Override
	public int save(ExternalTransaction theExternalTransaction) {

		ExternalTransaction dbExternalTransaction = entityManager.merge(theExternalTransaction);
		
		// update with id from db ... so we can get generated id for save/insert
		theExternalTransaction.setBankTransactionId(dbExternalTransaction.getBankTransactionId());

		return theExternalTransaction.getBankTransactionId();
		
	}

}











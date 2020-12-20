package ir.co.pna.exchange.dao.account;

import ir.co.pna.exchange.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class AccountDAOJpaImpl implements AccountDAO {

	private EntityManager entityManager;

	@Autowired
	public AccountDAOJpaImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Override
	public List<Account> findAll() {

		// create a query
		Query theQuery = 
				entityManager.createQuery("from Account");
		
		// execute query and get result list
		List<Account> accounts = theQuery.getResultList();
		
		// return the results		
		return accounts;
	}

	@Override
	public Account findById(int theId) {

		Account theAccount =
				entityManager.find(Account.class, theId);
		
		return theAccount;
	}

	@Override
	public long save(Account theAccount) {

		theAccount.setId(0);

		Account dbAccount = entityManager.merge(theAccount);
		
		// update with id from db ... so we can get generated id for save/insert
		theAccount.setId(dbAccount.getId());

		return theAccount.getId();
		
	}

}











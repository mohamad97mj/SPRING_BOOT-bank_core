package ir.co.pna.exchange.dao.Contract;

import ir.co.pna.exchange.entity.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class ContractDAOJpaImpl implements ContractDAO {

	private EntityManager entityManager;

	@Autowired
	public ContractDAOJpaImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Contract> findAll() {

		// create a query
		Query theQuery =
				entityManager.createQuery("from Contract");

		// execute query and get result list
		List<Contract> contracts = theQuery.getResultList();

		// return the results
		return contracts;
	}

	@Override
	public Contract findById(int theId) {

		Contract theContract =
				entityManager.find(Contract.class, theId);
		
		return theContract;
	}

	@Override
	public long save(Contract theContract) {

		Contract dbContract = entityManager.merge(theContract);
		
		// update with id from db ... so we can get generated id for save/insert
		theContract.setId(dbContract.getId());

		return theContract.getId();
		
	}

	@Override
	public void deleteById(int theId) {

		// delete object with primary key
		Query theQuery = entityManager.createQuery(
							"delete from Contract where id=:contractId");
		
		theQuery.setParameter("contractId", theId);
		
		theQuery.executeUpdate();
	}

}











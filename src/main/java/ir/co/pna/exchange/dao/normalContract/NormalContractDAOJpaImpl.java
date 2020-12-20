package ir.co.pna.exchange.dao.normalContract;

import ir.co.pna.exchange.entity.NormalContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class NormalContractDAOJpaImpl implements NormalContractDAO {

	private EntityManager entityManager;

	@Autowired
	public NormalContractDAOJpaImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<NormalContract> findAll() {

		// create a query
		Query theQuery =
				entityManager.createQuery("from NormalContract");

		// execute query and get result list
		List<NormalContract> normalContracts = theQuery.getResultList();

		// return the results
		return normalContracts;
	}

	@Override
	public NormalContract findById(int theId) {

		NormalContract theNormalContract =
				entityManager.find(NormalContract.class, theId);
		
		return theNormalContract;
	}

	@Override
	public NormalContract save(NormalContract theNormalContract) {

		NormalContract dbNormalContract = entityManager.merge(theNormalContract);
		
		// update with id from db ... so we can get generated id for save/insert
		theNormalContract.setId(dbNormalContract.getId());

		return theNormalContract;
		
	}

}











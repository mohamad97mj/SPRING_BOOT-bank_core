package ir.co.pna.exchange.dao.subcontract;

import ir.co.pna.exchange.entity.Subcontract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class SubcontractDAOJpaImpl implements SubcontractDAO {

	private EntityManager entityManager;

	@Autowired
	public SubcontractDAOJpaImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Subcontract> findAll() {

		// create a query
		Query theQuery =
				entityManager.createQuery("from Subcontract");

		// execute query and get result list
		List<Subcontract> subcontracts = theQuery.getResultList();

		// return the results
		return subcontracts;
	}

	@Override
	public Subcontract findById(int theId) {

		Subcontract theSubcontract =
				entityManager.find(Subcontract.class, theId);
		
		return theSubcontract;
	}

	@Override
	public Subcontract save(Subcontract theSubcontract) {

		Subcontract dbSubcontract = entityManager.merge(theSubcontract);
		
		// update with id from db ... so we can get generated id for save/insert
		theSubcontract.setId(dbSubcontract.getId());

		return theSubcontract;
		
	}

}











package ir.co.pna.exchange.dao.Owner;

import ir.co.pna.exchange.entity.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class OwnerDAOJpaImpl implements OwnerDAO {

	private EntityManager entityManager;

	@Autowired
	public OwnerDAOJpaImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Owner> findAll() {

		// create a query
		Query theQuery =
				entityManager.createQuery("from Owner");

		// execute query and get result list
		List<Owner> owners = theQuery.getResultList();

		// return the results
		return owners;
	}

	@Override
	public Owner findById(long theId) {

		Owner theOwner =
				entityManager.find(Owner.class, theId);
		
		return theOwner;
	}

	@Override
	public long save(Owner theOwner) {

		Owner dbOwner = entityManager.merge(theOwner);
		
		// update with id from db ... so we can get generated id for save/insert
		theOwner.setBankAccountId(dbOwner.getBankAccountId());

		return theOwner.getBankAccountId();
		
	}

	@Override
	public void deleteById(long theId) {

		// delete object with primary key
		Query theQuery = entityManager.createQuery(
							"delete from Owner where bankAccountId=:ownerId");
		
		theQuery.setParameter("ownerId", theId);
		
		theQuery.executeUpdate();
	}

}











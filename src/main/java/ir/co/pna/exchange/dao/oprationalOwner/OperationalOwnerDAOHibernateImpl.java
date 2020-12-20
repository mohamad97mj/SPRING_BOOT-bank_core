package ir.co.pna.exchange.dao.oprationalOwner;

import ir.co.pna.exchange.emum.OwnerType;
import ir.co.pna.exchange.entity.OperationalOwner;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class OperationalOwnerDAOHibernateImpl implements OperationalOwnerDAO {

	private EntityManager entityManager;

	@Autowired
	public OperationalOwnerDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<OperationalOwner> findAll() {

		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery =
				currentSession.createQuery("from OperationalOwner");

		// execute query and get result list
		List<OperationalOwner> operationalOwners = theQuery.getResultList();

		// return the results
		return operationalOwners;
	}


	@Override
	public OperationalOwner findByType(OwnerType type) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery =
				currentSession.createQuery("SELECT o FROM OperationalOwner o WHERE o.ownerType =: type ");
		theQuery.setParameter("type", type);
		OperationalOwner theOperationalOwner = (OperationalOwner)theQuery.getSingleResult();
		return theOperationalOwner;
	}


	@Override
	public String save(OperationalOwner theOperationalOwner) {

		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(theOperationalOwner);

		return theOperationalOwner.getBankAccountId();
	}

}











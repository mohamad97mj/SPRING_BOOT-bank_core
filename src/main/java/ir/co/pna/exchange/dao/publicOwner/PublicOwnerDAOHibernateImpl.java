package ir.co.pna.exchange.dao.publicOwner;

import ir.co.pna.exchange.emum.OwnerType;
import ir.co.pna.exchange.entity.Exporter;
import ir.co.pna.exchange.entity.PublicOwner;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class PublicOwnerDAOHibernateImpl implements PublicOwnerDAO {

	private EntityManager entityManager;

	@Autowired
	public PublicOwnerDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<PublicOwner> findAll() {

		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery =
				currentSession.createQuery("from PublicOwner");

		// execute query and get result list
		List<PublicOwner> publicOwners = theQuery.getResultList();

		// return the results
		return publicOwners;
	}

	@Override
	public PublicOwner findById(String theId) {
		Session currentSession = entityManager.unwrap(Session.class);

		PublicOwner thePublicOwner =
				currentSession.find(PublicOwner.class, theId);
		PublicOwner rValue;
		if(thePublicOwner.getOwnerType()== OwnerType.EXPORTER)
			rValue = currentSession.find(Exporter.class,theId);
		else
			rValue = thePublicOwner;
		return rValue;
	}

	@Override
	public String save(PublicOwner thePublicOwner) {

		Session currentSession = entityManager.unwrap(Session.class);
		
		// update with id from db ... so we can get generated id for save/insert
		currentSession.saveOrUpdate(thePublicOwner);

		return thePublicOwner.getBankAccountId();
		
	}

}











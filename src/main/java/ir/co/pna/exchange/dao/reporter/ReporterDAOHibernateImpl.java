package ir.co.pna.exchange.dao.reporter;

import ir.co.pna.exchange.entity.Judge;
import ir.co.pna.exchange.entity.Reporter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class ReporterDAOHibernateImpl implements ReporterDAO {

	private EntityManager entityManager;

	@Autowired
	public ReporterDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}


	@Override
	public List<Reporter> findAll() {

		// create a query
		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery =
				currentSession.createQuery("from Reporter ");

		// execute query and get result list
		List<Reporter> reporters = theQuery.getResultList();

		// return the results
		return reporters;
	}

	@Override
	public Reporter findById(String reporterId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Reporter theReporter =
				currentSession.get(Reporter.class, reporterId);
		
		return theReporter;
	}

	@Override
	public String save(Reporter theReporter) {

		Session currentSession = entityManager.unwrap(Session.class);
		// update with id from db ... so we can get generated id for save/insert
		currentSession.saveOrUpdate(theReporter);

		return theReporter.getId();
		
	}

}











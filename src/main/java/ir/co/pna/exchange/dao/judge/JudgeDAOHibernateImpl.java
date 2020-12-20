package ir.co.pna.exchange.dao.judge;

import ir.co.pna.exchange.emum.ContractStatus;
import ir.co.pna.exchange.entity.Judge;
import ir.co.pna.exchange.entity.NormalContract;
import ir.co.pna.exchange.entity.Subcontract;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class JudgeDAOHibernateImpl implements JudgeDAO {

	private EntityManager entityManager;

	@Autowired
	public JudgeDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Judge> findAll() {

		// create a query
		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery =
				currentSession.createQuery("from Judge");

		// execute query and get result list
		List<Judge> judges = theQuery.getResultList();

		// return the results
		return judges;
	}

	@Override
	public Judge findById(String judgeId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Judge theJudge =
				currentSession.get(Judge.class, judgeId);
		
		return theJudge;
	}

	@Override
	public String save(Judge theJudge) {

		Session currentSession = entityManager.unwrap(Session.class);
		// update with id from db ... so we can get generated id for save/insert
		currentSession.saveOrUpdate(theJudge);

		return theJudge.getId();
		
	}



	@Override
	public List<NormalContract> getJudgedNormalContracts(String judgeId) {
		Session currentSession = entityManager.unwrap(Session.class);
		// create a query
		Query theQuery = currentSession.createQuery("SELECT c FROM NormalContract c INNER JOIN c.judge j WHERE c.status = :status AND j.id = :judgeId");
		theQuery.setParameter("status", ContractStatus.JUDGED);
		theQuery.setParameter("judgeId", judgeId);
		// execute query and get result list
		List<NormalContract> theNormalContracts = theQuery.getResultList();

		// return the results

		return theNormalContracts;
	}

	@Override
	public List<NormalContract> getNotJudgedNormalContracts(String judgeId) {
		Session currentSession = entityManager.unwrap(Session.class);
		// create a query
		Query theQuery = currentSession.createQuery("SELECT c FROM NormalContract c INNER JOIN c.judge j WHERE c.status = :status AND j.id = :judgeId");
		theQuery.setParameter("status", ContractStatus.CLAIMED_BY_IMPORTER);
		theQuery.setParameter("judgeId", judgeId);
		// execute query and get result list
		List<NormalContract> theNormalContracts = theQuery.getResultList();

		// return the results

		return theNormalContracts;
	}

	@Override
	public NormalContract getJudgedNormalContract(String judgeId, int normalContractId) {
		Session currentSession = entityManager.unwrap(Session.class);
		// create a query
		Query theQuery = currentSession.createQuery("SELECT c FROM NormalContract c INNER JOIN c.judge j WHERE c.id = :normalContractId AND c.status = :status AND j.id = :judgeId");
		theQuery.setParameter("normalContractId", normalContractId);
		theQuery.setParameter("status", ContractStatus.JUDGED);
		theQuery.setParameter("judgeId", judgeId);
		// execute query and get result list
		NormalContract theNormalContract = (NormalContract)theQuery.getSingleResult();

		// return the results

		return theNormalContract;
	}

	@Override
	public NormalContract getNotJudgedNormalContract(String judgeId, int normalContractId) {
		Session currentSession = entityManager.unwrap(Session.class);
		// create a query
		Query theQuery = currentSession.createQuery("SELECT c FROM NormalContract c INNER JOIN c.judge j WHERE c.id = :normalContractId AND c.status = :status AND j.id = :judgeId");
		theQuery.setParameter("normalContractId", normalContractId);
		theQuery.setParameter("status", ContractStatus.CLAIMED_BY_IMPORTER);
		theQuery.setParameter("judgeId", judgeId);
		// execute query and get result list
		NormalContract theNormalContract = (NormalContract)theQuery.getSingleResult();

		// return the results

		return theNormalContract;
	}

	@Override
	public List<Subcontract> getJudgedNormalContractSubcontracts(String judgeId, int normalContractId) {
		Session currentSession = entityManager.unwrap(Session.class);
		// create a query
		Query theQuery = currentSession.createQuery("SELECT s FROM Subcontract s INNER JOIN s.parent p INNER JOIN p.judge j WHERE p.id = :normalContractId AND p.status = :normalContractStatus AND s.status = :subcontractStatus AND j.id = :judgeId");
		theQuery.setParameter("normalContractId", normalContractId);
		theQuery.setParameter("normalContractStatus", ContractStatus.JUDGED);
		theQuery.setParameter("subcontractStatus", ContractStatus.JUDGED);
		theQuery.setParameter("judgeId", judgeId);
		// execute query and get result list
		List<Subcontract> theSubcontracts = theQuery.getResultList();

		// return the results
		return theSubcontracts;
	}

	@Override
	public List<Subcontract> getNotJudgedNormalContractJudgedSubcontracts(String judgeId, int normalContractId) {
		Session currentSession = entityManager.unwrap(Session.class);
		// create a query
		Query theQuery = currentSession.createQuery("SELECT s FROM Subcontract s INNER JOIN s.parent p INNER JOIN p.judge j WHERE p.id = :normalContractId AND p.status = :normalContractStatus AND s.status = :subcontractStatus AND j.id = :judgeId");
		theQuery.setParameter("normalContractId", normalContractId);
		theQuery.setParameter("normalContractStatus", ContractStatus.CLAIMED_BY_IMPORTER);
		theQuery.setParameter("subcontractStatus", ContractStatus.JUDGED);
		theQuery.setParameter("judgeId", judgeId);
		// execute query and get result list
		List<Subcontract> theSubcontracts = theQuery.getResultList();

		// return the results
		return theSubcontracts;
	}

	@Override
	public List<Subcontract> getNotJudgedNormalContractNotJudgedSubcontracts(String judgeId, int normalContractId) {
		Session currentSession = entityManager.unwrap(Session.class);
		// create a query
		Query theQuery = currentSession.createQuery("SELECT s FROM Subcontract s INNER JOIN s.parent p INNER JOIN p.judge j WHERE p.id = :normalContractId AND p.status = :normalContractStatus AND s.status = :subcontractStatus AND j.id = :judgeId");
		theQuery.setParameter("normalContractId", normalContractId);
		theQuery.setParameter("normalContractStatus", ContractStatus.CLAIMED_BY_IMPORTER);
		theQuery.setParameter("subcontractStatus", ContractStatus.CLAIMED_BY_IMPORTER);
		theQuery.setParameter("judgeId", judgeId);
		// execute query and get result list
		List<Subcontract> theSubcontracts = theQuery.getResultList();

		// return the results
		return theSubcontracts;
	}


	@Override
	public Subcontract getNormalContractSubcontract(String judgeId, int normalContractId,  int subcontractId) {
		Session currentSession = entityManager.unwrap(Session.class);
		// create a query
		Query theQuery = currentSession.createQuery("SELECT s FROM Subcontract s INNER JOIN s.parent p INNER JOIN p.judge j WHERE s.id = :subcontractId AND p.id = :normalContractId AND j.id = :judgeId");
		theQuery.setParameter("normalContractId", normalContractId);
		theQuery.setParameter("subcontractId", subcontractId);
		theQuery.setParameter("judgeId", judgeId);
		// execute query and get result list
		Subcontract theSubcontract = (Subcontract)theQuery.getSingleResult();

		// return the results
		return theSubcontract;
	}


}











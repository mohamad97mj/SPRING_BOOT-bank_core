package ir.co.pna.exchange.dao.Judge;

import ir.co.pna.exchange.entity.Judge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class JudgeDAOJpaImpl implements JudgeDAO {

	private EntityManager entityManager;

	@Autowired
	public JudgeDAOJpaImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Judge> findAll() {

		// create a query
		Query theQuery =
				entityManager.createQuery("from Judge");

		// execute query and get result list
		List<Judge> judges = theQuery.getResultList();

		// return the results
		return judges;
	}

	@Override
	public Judge findById(long theId) {

		Judge theJudge =
				entityManager.find(Judge.class, theId);
		
		return theJudge;
	}

	@Override
	public long save(Judge theJudge) {

		Judge dbJudge = entityManager.merge(theJudge);
		
		// update with id from db ... so we can get generated id for save/insert
		theJudge.setNationalId(dbJudge.getNationalId());

		return theJudge.getNationalId();
		
	}

	@Override
	public void deleteById(long theId) {

		// delete object with primary key
		Query theQuery = entityManager.createQuery(
							"delete from Judge where nationalId=:judgeId");
		
		theQuery.setParameter("judgeId", theId);
		
		theQuery.executeUpdate();
	}

}











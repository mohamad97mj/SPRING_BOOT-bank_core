package ir.co.pna.exchange.dao.User;

import ir.co.pna.exchange.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class UserDAOJpaImpl implements UserDAO {

	private EntityManager entityManager;

	@Autowired
	public UserDAOJpaImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<User> findAll() {

		// create a query
		Query theQuery =
				entityManager.createQuery("from User");

		// execute query and get result list
		List<User> users = theQuery.getResultList();

		// return the results
		return users;
	}

	@Override
	public User findById(String userId) {

		Session currentSession = entityManager.unwrap(Session.class);

		User theUser =
				entityManager.find(User.class, userId);
		
		return theUser;
	}

	@Override
	public String save(User theUser) {

		User dbUser = entityManager.merge(theUser);
		
		// update with id from db ... so we can get generated id for save/insert
		theUser.setNationalCode(dbUser.getNationalCode());

		return theUser.getNationalCode();
		
	}

	@Override
	public void deleteById(String userId) {

		// delete object with primary key
		Query theQuery = entityManager.createQuery(
							"delete from User where nationalCode=:userId");
		
		theQuery.setParameter("userId", userId);
		
		theQuery.executeUpdate();
	}
}











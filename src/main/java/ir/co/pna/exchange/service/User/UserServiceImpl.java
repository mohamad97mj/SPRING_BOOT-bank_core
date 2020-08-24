package ir.co.pna.exchange.service.User;

import ir.co.pna.exchange.dao.User.UserDAO;
import ir.co.pna.exchange.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

	private UserDAO userDAO;
	
	@Autowired
	public UserServiceImpl(@Qualifier("userDAOJpaImpl") UserDAO theUserDAO) {
		userDAO = theUserDAO;
	}
	
	@Override
	@Transactional
	public List<User> findAll() {
		return userDAO.findAll();
	}

	@Override
	@Transactional
	public User findById(long theId) {
		return userDAO.findById(theId);
	}

	@Override
	@Transactional
	public long save(User User) {
		return userDAO.save(User);
	}

	@Override
	@Transactional
	public void deleteById(long theId) {
		userDAO.deleteById(theId);
	}

}







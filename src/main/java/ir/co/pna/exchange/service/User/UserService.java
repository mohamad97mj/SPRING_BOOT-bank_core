package ir.co.pna.exchange.service.User;

import ir.co.pna.exchange.entity.Transaction;
import ir.co.pna.exchange.entity.User;

import java.util.List;


public interface UserService {

	public List<User> findAll();

	public User findById(long id);

	public long save(User user);

	public void deleteById(long id);

}

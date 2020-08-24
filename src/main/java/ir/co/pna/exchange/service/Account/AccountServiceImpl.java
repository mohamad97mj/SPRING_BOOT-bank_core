package ir.co.pna.exchange.service.Account;

import ir.co.pna.exchange.dao.Account.AccountDAO;
import ir.co.pna.exchange.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {

	private AccountDAO accountDAO;
	
	@Autowired
	public AccountServiceImpl(@Qualifier("accountDAOJpaImpl") AccountDAO theAccountDAO) {
		accountDAO = theAccountDAO;
	}
	
	@Override
	@Transactional
	public List<Account> findAll() {
		return accountDAO.findAll();
	}

	@Override
	@Transactional
	public Account findById(int theId) {
		return accountDAO.findById(theId);
	}

	@Override
	@Transactional
	public long save(Account account) {
		return accountDAO.save(account);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		accountDAO.deleteById(theId);
	}

}







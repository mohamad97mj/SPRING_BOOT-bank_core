package ir.co.pna.exchange.service.account;

import ir.co.pna.exchange.dao.account.AccountDAO;
import ir.co.pna.exchange.entity.Account;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
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
		Account theAccount = accountDAO.findById(theId);
		if (theAccount == null) {
			throw new MyEntityNotFoundException("account id not found - " + theId);
		}
		return theAccount;
	}

	@Override
	@Transactional
	public long save(Account account) {
		return accountDAO.save(account);
	}

}







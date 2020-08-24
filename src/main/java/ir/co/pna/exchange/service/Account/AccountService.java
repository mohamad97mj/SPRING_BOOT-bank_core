package ir.co.pna.exchange.service.Account;

import ir.co.pna.exchange.entity.Account;
import ir.co.pna.exchange.entity.Employee;

import java.util.List;


public interface AccountService {

	public List<Account> findAll();

	public Account findById(int id);

	public long save(Account account);

	public void deleteById(int id);

}

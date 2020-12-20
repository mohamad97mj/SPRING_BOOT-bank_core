package ir.co.pna.exchange.service.account;

import ir.co.pna.exchange.entity.Account;

import java.util.List;


public interface AccountService {

	List<Account> findAll();

	Account findById(int id);

	long save(Account account);

}

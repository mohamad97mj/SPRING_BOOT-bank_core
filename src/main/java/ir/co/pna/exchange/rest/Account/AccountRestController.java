package ir.co.pna.exchange.rest.Account;

import ir.co.pna.exchange.entity.Account;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import ir.co.pna.exchange.service.Account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class AccountRestController {

	private AccountService accountService;
	
	@Autowired
	public AccountRestController(AccountService theAccountService) {
		accountService = theAccountService;
	}
	
	@GetMapping("/accounts")
	public List<Account> findAll() {
		return accountService.findAll();
	}


	@GetMapping("/accounts/{accountId}")
	public Account getAccount(@PathVariable int accountId) {
		
		Account theAccount = accountService.findById(accountId);
		
		if (theAccount == null) {
			throw new MyEntityNotFoundException("account id not found - " + accountId);
		}

		return theAccount;
	}
	

	@PostMapping("/accounts")
	public Account addAccount(@RequestBody Account theAccount) {
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
		
		theAccount.setId(0);
		
		accountService.save(theAccount);
		
		return theAccount;
	}
	
	// add mapping for PUT /Accounts - update existing Account
	
	@PutMapping("/accounts")
	public Account updateAccount(@RequestBody Account theAccount) {
		
		accountService.save(theAccount);
		
		return theAccount;
	}
	

	@DeleteMapping("/accounts/{accountId}")
	public String deleteAccount(@PathVariable int accountId) {
		
		Account tempAccount = accountService.findById(accountId);
		
		// throw exception if null
		
		if (tempAccount == null) {
			throw new MyEntityNotFoundException("Account id not found - " + accountId);
		}
		
		accountService.deleteById(accountId);
		
		return "Deleted account id - " + accountId;
	}
	
}











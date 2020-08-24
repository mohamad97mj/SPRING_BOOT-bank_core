package ir.co.pna.exchange.rest.Transaction;

import ir.co.pna.exchange.entity.Transaction;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import ir.co.pna.exchange.service.Transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class TransactionRestController {

	private TransactionService transactionService;
	
	@Autowired
	public TransactionRestController(TransactionService theTransactionService) {
		transactionService = theTransactionService;
	}
	
	@GetMapping("/transactions")
	public List<Transaction> findAll() {
		return transactionService.findAll();
	}


	@GetMapping("/transactions/{transactionId}")
	public Transaction getTransaction(@PathVariable int transactionId) {
		
		Transaction theTransaction = transactionService.findById(transactionId);
		
		if (theTransaction == null) {
			throw new MyEntityNotFoundException("Transaction id not found - " + transactionId);
		}
		
		return theTransaction;
	}
	

	@PostMapping("/transactions")
	public Transaction addTransaction(@RequestBody Transaction theTransaction) {
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
		
		theTransaction.setId(0);
		
		transactionService.save(theTransaction);
		
		return theTransaction;
	}
	
	// add mapping for PUT /Transactions - update existing Transaction
	
	@PutMapping("/transactions")
	public Transaction updateTransaction(@RequestBody Transaction theTransaction) {
		
		transactionService.save(theTransaction);
		
		return theTransaction;
	}
	

	@DeleteMapping("/transactions/{transactionId}")
	public String deleteTransaction(@PathVariable int transactionId) {
		
		Transaction tempTransaction = transactionService.findById(transactionId);
		
		// throw exception if null
		
		if (tempTransaction == null) {
			throw new MyEntityNotFoundException("Transaction id not found - " + transactionId);
		}
		
		transactionService.deleteById(transactionId);
		
		return "Deleted transaction id - " + transactionId;
	}
	
}











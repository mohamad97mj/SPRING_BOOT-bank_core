package ir.co.pna.exchange.rest.ExternalTransaction;

import ir.co.pna.exchange.entity.ExternalTransaction;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import ir.co.pna.exchange.service.ExternalTransaction.ExternalTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ExternalTransactionRestController {

	private ExternalTransactionService externalTransactionService;
	
	@Autowired
	public ExternalTransactionRestController(ExternalTransactionService theExternalTransactionService) {
		externalTransactionService = theExternalTransactionService;
	}
	
	@GetMapping("/externalTransactions")
	public List<ExternalTransaction> findAll() {
		return externalTransactionService.findAll();
	}


	@GetMapping("/externalTransactions/{externalTransactionId}")
	public ExternalTransaction getExternalTransaction(@PathVariable int externalTransactionId) {

		ExternalTransaction theExternalTransaction = externalTransactionService.findById(externalTransactionId);

		if (theExternalTransaction == null) {
			throw new MyEntityNotFoundException("ExternalTransaction id not found - " + externalTransactionId);
		}

		return theExternalTransaction;
	}


	@PostMapping("/externalTransactions")
	public ExternalTransaction addExternalTransaction(@RequestBody ExternalTransaction theExternalTransaction) {

		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update

		theExternalTransaction.setBankTransactionId(0);

		externalTransactionService.save(theExternalTransaction);

		return theExternalTransaction;
	}

	// add mapping for PUT /ExternalTransactions - update existing ExternalTransaction

	@PutMapping("/externalTransactions")
	public ExternalTransaction updateExternalTransaction(@RequestBody ExternalTransaction theExternalTransaction) {

		externalTransactionService.save(theExternalTransaction);

		return theExternalTransaction;
	}


	@DeleteMapping("/externalTransactions/{externalTransactionId}")
	public String deleteExternalTransaction(@PathVariable int externalTransactionId) {
		
		ExternalTransaction tempExternalTransaction = externalTransactionService.findById(externalTransactionId);
		
		// throw exception if null
		
		if (tempExternalTransaction == null) {
			throw new MyEntityNotFoundException("ExternalTransaction id not found - " + externalTransactionId);
		}
		
		externalTransactionService.deleteById(externalTransactionId);
		
		return "Deleted externalTransaction id - " + externalTransactionId;
	}
	
}











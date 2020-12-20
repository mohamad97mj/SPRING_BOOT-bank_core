package ir.co.pna.exchange.rest.externalTransaction;

import ir.co.pna.exchange.entity.ExternalTransaction;
import ir.co.pna.exchange.service.externalTransaction.ExternalTransactionService;
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
	
	@GetMapping("/externaltransactions")
	public List<ExternalTransaction> findAll() {
		return externalTransactionService.findAll();
	}


	@GetMapping("/externaltransactions/{externalTransactionId}")
	public ExternalTransaction getExternalTransaction(@PathVariable int externalTransactionId) {

		ExternalTransaction theExternalTransaction = externalTransactionService.findById(externalTransactionId);

		return theExternalTransaction;
	}


	@PostMapping("/externaltransactions")
	public ExternalTransaction addExternalTransaction(@RequestBody ExternalTransaction theExternalTransaction) {

		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update

		theExternalTransaction.setBankTransactionId(0);

		externalTransactionService.save(theExternalTransaction);

		return theExternalTransaction;
	}

	// add mapping for PUT /ExternalTransactions - update existing ExternalTransaction

	@PutMapping("/externaltransactions")
	public ExternalTransaction updateExternalTransaction(@RequestBody ExternalTransaction theExternalTransaction) {

		externalTransactionService.save(theExternalTransaction);

		return theExternalTransaction;
	}

}






package ir.co.pna.exchange.rest.Contract;

import ir.co.pna.exchange.entity.Contract;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import ir.co.pna.exchange.service.NormalContract.NormalContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class SubcontractRestController {

	private NormalContractService contractService;

	@Autowired
	public SubcontractRestController(NormalContractService theContractService) {
		contractService = theContractService;
	}
	
	@GetMapping("/contracts")
	public List<Contract> findAll() {
		return contractService.findAll();
	}


	@GetMapping("/contracts/{contractId}")
	public Contract getContract(@PathVariable int contractId) {
		
		Contract theContract = contractService.findById(contractId);
		
		if (theContract == null) {
			throw new MyEntityNotFoundException("Contract id not found - " + contractId);
		}
		
		return theContract;
	}
	

	@PostMapping("/contracts")
	public Contract addContract(@RequestBody Contract theContract) {
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
		
		theContract.setId(0);
		
		contractService.save(theContract);
		
		return theContract;
	}
	
	// add mapping for PUT /Contracts - update existing Contract
	
	@PutMapping("/contracts")
	public Contract updateContract(@RequestBody Contract theContract) {
		
		contractService.save(theContract);
		
		return theContract;
	}
	

	@DeleteMapping("/Ccntracts/{contractId}")
	public String deleteContract(@PathVariable int contractId) {
		
		Contract tempContract = contractService.findById(contractId);
		
		// throw exception if null
		
		if (tempContract == null) {
			throw new MyEntityNotFoundException("Contract id not found - " + contractId);
		}
		
		contractService.deleteById(contractId);
		
		return "Deleted contract id - " + contractId;
	}
	
}











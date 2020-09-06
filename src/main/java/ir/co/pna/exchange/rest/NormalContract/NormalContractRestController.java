package ir.co.pna.exchange.rest.NormalContract;

import ir.co.pna.exchange.entity.Contract;
import ir.co.pna.exchange.entity.NormalContract;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import ir.co.pna.exchange.service.NormalContract.NormalContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class NormalContractRestController {

	private NormalContractService normalContractService;
	
	@Autowired
	public NormalContractRestController(NormalContractService normalContractService) {
		this.normalContractService = normalContractService;
	}
	
	@GetMapping("/normalcontracts")
	public List<NormalContract> findAll() {
		return normalContractService.findAll();
	}


	@GetMapping("/normalcontracts/{contractId}")
	public NormalContract getContract(@PathVariable int contractId) {
		
		NormalContract theNormalContract = normalContractService.findById(contractId);
		
		if (theNormalContract == null) {
			throw new MyEntityNotFoundException("NormalContract id not found - " + contractId);
		}
		
		return theNormalContract;
	}
	

	@PostMapping("/normalcontracts")
	public Contract addContract(@RequestBody NormalContract theNormalContract) {
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
		
		theNormalContract.setId(0);
		
		normalContractService.save(theNormalContract);
		
		return theNormalContract;
	}
	
	// add mapping for PUT /Contracts - update existing Contract
	
	@PutMapping("/normalcontracts")
	public Contract updateContract(@RequestBody NormalContract theNormalContract) {
		
		normalContractService.save(theNormalContract);
		
		return theNormalContract;
	}
	

	@DeleteMapping("/normalcontracts/{contractId}")
	public String deleteContract(@PathVariable int contractId) {
		
		NormalContract tempContract = normalContractService.findById(contractId);
		
		// throw exception if null
		
		if (tempContract == null) {
			throw new MyEntityNotFoundException("Contract id not found - " + contractId);
		}
		
		normalContractService.deleteById(contractId);
		
		return "Deleted contract id - " + contractId;
	}
	
}











package ir.co.pna.exchange.rest.subcontract;

import ir.co.pna.exchange.entity.Subcontract;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import ir.co.pna.exchange.service.subcontract.SubcontractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class SubcontractRestController {

	private SubcontractService subcontractService;

	@Autowired
	public SubcontractRestController(SubcontractService theSubcontractService) {
		subcontractService = theSubcontractService;
	}
	
	@GetMapping("/subcontracts")
	public List<Subcontract> findAll() {
		return subcontractService.findAll();
	}


	@GetMapping("/subcontracts/{contractId}")
	public Subcontract getContract(@PathVariable int contractId) {
		
		Subcontract theSubcontract = subcontractService.findById(contractId);
		
		if (theSubcontract == null) {
			throw new MyEntityNotFoundException("Subcontract id not found - " + contractId);
		}
		
		return theSubcontract;
	}

}











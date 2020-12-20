package ir.co.pna.exchange.rest.reporter;

import ir.co.pna.exchange.entity.Account;
import ir.co.pna.exchange.entity.Reporter;
import ir.co.pna.exchange.service.account.AccountService;
import ir.co.pna.exchange.service.reporter.ReporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ReporterRestController {

	private ReporterService reporterService;
	
	@Autowired
	public ReporterRestController(ReporterService theReporterService) {
		reporterService = theReporterService;
	}
	
	@GetMapping("/reporters")
	public List<Reporter> findAll() {
		return reporterService.findAll();
	}


	@GetMapping("/reporters/{reporterId}")
	public Reporter getReporter(@PathVariable String reporterId) {
		
		Reporter theReporter = reporterService.findById(reporterId);

		return theReporter;
	}
	

	@PostMapping("/reporters")
	public Reporter addReporter(@RequestBody Reporter theReporter) {
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
		
		theReporter.setId(null);
		
		reporterService.save(theReporter);
		
		return theReporter;
	}
	
	// add mapping for PUT /Accounts - update existing Account
	
}











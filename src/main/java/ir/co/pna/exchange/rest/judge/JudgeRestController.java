package ir.co.pna.exchange.rest.judge;

import ir.co.pna.exchange.entity.Judge;
import ir.co.pna.exchange.entity.NormalContract;
import ir.co.pna.exchange.entity.Subcontract;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import ir.co.pna.exchange.service.judge.JudgeService;
import ir.co.pna.exchange.service.subcontract.SubcontractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class JudgeRestController {

	private JudgeService judgeService;
	private SubcontractService subcontractService;

	@Autowired
	public JudgeRestController(JudgeService theJudgeService, SubcontractService theSubcontractService) {
		judgeService = theJudgeService;
		subcontractService = theSubcontractService;
	}
	
	@GetMapping("/judges")
	public List<Judge> findAll() {
		return judgeService.findAll();
	}

	@GetMapping("/judges/{judgeId}")
	public Judge getJudge(@PathVariable String judgeId) {
		
		Judge theJudge = judgeService.findById(judgeId);
		
		if (theJudge == null) {
			throw new MyEntityNotFoundException("Judge id not found - " + judgeId);
		}
		
		return theJudge;
	}
	

	@PostMapping("/judges")
	public Judge addJudge(@RequestBody Judge theJudge) {
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
		
		theJudge.setId(null);
		
		judgeService.save(theJudge);
		
		return theJudge;
	}
	
	// add mapping for PUT /Judges - update existing Judge
	
	@PutMapping("/judges")
	public Judge updateJudge(@RequestBody Judge theJudge) {
		
		judgeService.save(theJudge);
		
		return theJudge;
	}

	@GetMapping("/judges/{judgeId}/judgednormalcontracts")
	public List<NormalContract> getJudgedNormalContracts(@PathVariable String judgeId){
		return judgeService.getJudgedNormalContracts(judgeId);
	}

	@GetMapping("/judges/{judgeId}/notjudgednormalcontracts")
	public List<NormalContract> getNotJudgedNormalContracts(@PathVariable String judgeId){
		return judgeService.getNotJudgedNormalContracts(judgeId);
	}

	@GetMapping("/judges/{judgeId}/judgednormalcontracts/{normalContractId}")
	public NormalContract getJudgedNormalContract(@PathVariable String judgeId, @PathVariable int normalContractId){
		return judgeService.getJudgedNormalContract(judgeId, normalContractId);
	}

	@GetMapping("/judges/{judgeId}/notjudgednormalcontracts/{normalContractId}")
	public NormalContract getNotJudgedNormalContract(@PathVariable String judgeId, @PathVariable int normalContractId){
		return judgeService.getNotJudgedNormalContract(judgeId, normalContractId);
	}

	@GetMapping("/judges/{judgeId}/judgednormalcontracts/{normalContractId}/subcontracts")
	public List<Subcontract> getJudgedNormalContractSubcontracts(@PathVariable String judgeId, @PathVariable int normalContractId){
		return judgeService.getJudgedNormalContractSubcontracts(judgeId, normalContractId);
	}

	@GetMapping("/judges/{judgeId}/notjudgednormalcontracts/{normalContractId}/judgedsubcontracts")
	public List<Subcontract> getJudgedNormalContractJudgedSubcontracts(@PathVariable String judgeId, @PathVariable int normalContractId){
		return judgeService.getNotJudgedNormalContractJudgedSubcontracts(judgeId, normalContractId);
	}

	@GetMapping("/judges/{judgeId}/notjudgednormalcontracts/{normalContractId}/notjudgedsubcontracts")
	public List<Subcontract> getJudgedNormalContractNotJudgedSubcontracts(@PathVariable String judgeId, @PathVariable int normalContractId){
		return judgeService.getNotJudgedNormalContractNotJudgedSubcontracts(judgeId, normalContractId);
	}

	@GetMapping("/judges/{judgeId}/normalcontracts/{normalContractId}/subcontracts/{subcontractId}")
	public Subcontract getNormalContractSubcontract(@PathVariable String judgeId, @PathVariable int normalContractId, @PathVariable int subcontractId){
		return judgeService.getNormalContractSubcontract(judgeId, normalContractId, subcontractId);
	}

	@PutMapping("/judges/{judgeId}/normalcontracts/{normalContractId}/subcontracts/{subcontractId}/judge")
	public Subcontract judgeNormalContractSubcontract(@PathVariable String judgeId, @PathVariable int normalContractId, @PathVariable int subcontractId, @RequestBody Map<String, Object> payload) {
		Subcontract theSubcontract = judgeService.getNormalContractSubcontract(judgeId, normalContractId, subcontractId);
		payload.put("operator_national_id", judgeId);
		return subcontractService.judge(theSubcontract, payload);
	}
}











package ir.co.pna.exchange.rest.Judge;

import ir.co.pna.exchange.entity.Judge;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import ir.co.pna.exchange.service.Judge.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class JudgeRestController {

	private JudgeService judgeService;
	
	@Autowired
	public JudgeRestController(JudgeService theJudgeService) {
		judgeService = theJudgeService;
	}
	
	@GetMapping("/judges")
	public List<Judge> findAll() {
		return judgeService.findAll();
	}

	@GetMapping("/judges/{judgeId}")
	public Judge getJudge(@PathVariable int judgeId) {
		
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
		
		theJudge.setNationalId(0);
		
		judgeService.save(theJudge);
		
		return theJudge;
	}
	
	// add mapping for PUT /Judges - update existing Judge
	
	@PutMapping("/judges")
	public Judge updateJudge(@RequestBody Judge theJudge) {
		
		judgeService.save(theJudge);
		
		return theJudge;
	}
	

	@DeleteMapping("/judges/{judgeId}")
	public String deleteJudge(@PathVariable int judgeId) {
		
		Judge tempJudge = judgeService.findById(judgeId);
		
		// throw exception if null
		
		if (tempJudge == null) {
			throw new MyEntityNotFoundException("Judge id not found - " + judgeId);
		}
		
		judgeService.deleteById(judgeId);
		
		return "Deleted judge id - " + judgeId;
	}
	
}











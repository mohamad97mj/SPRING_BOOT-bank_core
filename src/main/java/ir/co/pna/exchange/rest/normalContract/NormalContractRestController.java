package ir.co.pna.exchange.rest.normalContract;

import ir.co.pna.exchange.entity.NormalContract;
import ir.co.pna.exchange.entity.Subcontract;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import ir.co.pna.exchange.service.normalContract.NormalContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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


    @GetMapping("/normalcontracts/{normalContractId}")
    public NormalContract getNormalContract(@PathVariable int normalContractId) {

        NormalContract theNormalContract = normalContractService.findById(normalContractId);

        if (theNormalContract == null) {
            throw new MyEntityNotFoundException("NormalContract id not found - " + normalContractId);
        }

        return theNormalContract;
    }


    @PostMapping("/normalcontracts")
//	public Contract addContract(@RequestBody NormalContract theNormalContract) {
    public NormalContract addNormalContract(@RequestBody Map<String, Object> payload) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        return normalContractService.save(payload);
    }

    // add mapping for PUT /Contracts - update existing Contract

    @GetMapping("/normalcontracts/{normalContractId}/subcontracts")
    public List<Subcontract> getNormalContractSubcontracts(@PathVariable int normalContractId) {

        NormalContract theNormalContract = normalContractService.findById(normalContractId);
        return theNormalContract.getSubcontracts();
//        return normalContractService.getSubcontracts(normalContractId);
    }


    @PutMapping("/normalcontracts/{normalContractId}/charge")
    public NormalContract chargeNormalContract(@PathVariable int normalContractId, @RequestBody Map<String, Object> payload){
        NormalContract theNormalContract = normalContractService.findById(normalContractId);
        return normalContractService.charge(theNormalContract, payload);
    }

    @PutMapping("/normalcontracts/{normalContractId}")
    public NormalContract updateNormalContract(@PathVariable int normalContractId, @RequestBody Map<String, Object> payload) {
        NormalContract theNormalcontract = normalContractService.findById(normalContractId);
        return normalContractService.update(theNormalcontract, payload);
    }

}











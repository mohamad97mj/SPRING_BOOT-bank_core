package ir.co.pna.exchange.rest.OperationalOwner;

import ir.co.pna.exchange.emum.OwnerType;
import ir.co.pna.exchange.entity.ExternalTransaction;
import ir.co.pna.exchange.entity.OperationalOwner;
import ir.co.pna.exchange.entity.PublicOwner;
import ir.co.pna.exchange.service.operationalOwner.OperationalOwnerService;
import ir.co.pna.exchange.service.publicOwner.PublicOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class OperationalOwnerRestController {

    private OperationalOwnerService operationalOwnerService;

    @Autowired
    public OperationalOwnerRestController(OperationalOwnerService theOperationalOwnerService) {
        operationalOwnerService = theOperationalOwnerService;
    }

    @GetMapping("/operationalowners")
    public List<OperationalOwner> findAll() {

        return operationalOwnerService.findAll();
    }


    @GetMapping("/operationalowners/{ownerType}")
    public OperationalOwner getOperationalOwner(@PathVariable String ownerType) {

        return operationalOwnerService.findByType(OwnerType.valueOf(ownerType.toUpperCase()));

    }

    @PostMapping("/operationalowners")
    public OperationalOwner addOperationalOwner(@RequestBody OperationalOwner theOperationalOwner) {

        operationalOwnerService.save(theOperationalOwner);

        return theOperationalOwner;
    }


    // more queries ............................................................


    @GetMapping("/operationalowners/{ownerType}/inexternaltransactions")
    public List<ExternalTransaction> getOwnerInExternalTransactions(@PathVariable String ownerType) {
        return operationalOwnerService.getInExternalTransactions(OwnerType.valueOf(ownerType.toUpperCase()));
    }


    @GetMapping("/operationalowners/{ownerType}/outexternaltransactions")
    public List<ExternalTransaction> getOwnerOutExternalTransactions(@PathVariable String ownerType) {
        return operationalOwnerService.getOutExternalTransactions(OwnerType.valueOf(ownerType.toUpperCase()));
    }


    @GetMapping("/operationalowners/{ownerType}/inexternaltransactions/timeinterval")
    public List<ExternalTransaction> getOwnerInExternalTransactionsTimeInterval(@PathVariable String ownerType, @RequestParam String from, @RequestParam String to) {
        long from_time = Long.parseLong(from);
        long to_time = Long.parseLong(to);
        return operationalOwnerService.getInExternalTransactionsTimeInterval(OwnerType.valueOf(ownerType.toUpperCase()), from_time, to_time);
    }

    @GetMapping("/operationalowners/{ownerType}/outexternaltransactions/timeinterval")
    public List<ExternalTransaction> getOwnerOutExternalTransactionsTimeInterval(@PathVariable String ownerType, @RequestParam String from, @RequestParam String to) {
        long from_time = Long.parseLong(from);
        long to_time = Long.parseLong(to);
        return operationalOwnerService.getOutExternalTransactionsTimeInterval(OwnerType.valueOf(ownerType.toUpperCase()), from_time, to_time);
    }

}











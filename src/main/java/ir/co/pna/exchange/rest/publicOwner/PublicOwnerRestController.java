package ir.co.pna.exchange.rest.publicOwner;

import ir.co.pna.exchange.entity.*;
import ir.co.pna.exchange.service.publicOwner.PublicOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class PublicOwnerRestController {

    private PublicOwnerService publicOwnerService;

    @Autowired
    public PublicOwnerRestController(PublicOwnerService thePublicOwnerService) {
        publicOwnerService = thePublicOwnerService;
    }

    @GetMapping("/publicowners")
    public List<PublicOwner> findAll() {

        return publicOwnerService.findAll();
    }


    @GetMapping("/publicowners/{ownerId}")
    public PublicOwner getOwner(@PathVariable String ownerId) {

        return publicOwnerService.findById(ownerId);

    }


    @PostMapping("/publicowners")
    public PublicOwner addPublicOwner(@RequestBody PublicOwner thePublicOwner) {

        publicOwnerService.save(thePublicOwner);

        return thePublicOwner;
    }


    // more queries ............................................................


    @GetMapping("/publicowners/{ownerId}/inexternaltransactions")
    public List<ExternalTransaction> getOwnerInExternalTransactions(@PathVariable String ownerId) {
        return publicOwnerService.getInExternalTransactions(ownerId);
    }


    @GetMapping("/publicowners/{ownerId}/outexternaltransactions")
    public List<ExternalTransaction> getOwnerOutExternalTransactions(@PathVariable String ownerId) {
        return publicOwnerService.getOutExternalTransactions(ownerId);
    }

}











package ir.co.pna.exchange.rest.Owner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.co.pna.exchange.entity.Owner;
import ir.co.pna.exchange.entity.User;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import ir.co.pna.exchange.service.Owner.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class OwnerRestController {

    private OwnerService ownerService;

    @Autowired
    public OwnerRestController(OwnerService theOwnerService) {
        ownerService = theOwnerService;
    }

    @GetMapping("/owners")
    public List<Owner> findAll() {
        return ownerService.findAll();
    }


    @GetMapping("/owners/{ownerId}")
    public Owner getOwner(@PathVariable long ownerId) {

        Owner theOwner = ownerService.findById(ownerId);

        if (theOwner == null) {
            throw new MyEntityNotFoundException("Owner id not found - " + ownerId);
        }

        return theOwner;
    }


    @PostMapping("/owners")
    public Owner addOwner(@RequestBody Owner theOwner) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        theOwner.setBankAccountId(0);

        ownerService.save(theOwner);

        return theOwner;
    }

    // add mapping for PUT /Owners - update existing Owner

    @PutMapping("/owners")
    public Owner updateOwner(@RequestBody Owner theOwner) {

        ownerService.save(theOwner);

        return theOwner;
    }


    @DeleteMapping("/owners/{ownerId}")
    public String deleteOwner(@PathVariable int ownerId) {

        Owner tempOwner = ownerService.findById(ownerId);

        // throw exception if null

        if (tempOwner == null) {
            throw new MyEntityNotFoundException("Owner id not found - " + ownerId);
        }

        ownerService.deleteById(ownerId);

        return "Deleted owner id - " + ownerId;
    }

    // more queries ............................................................

    @GetMapping("/owners/{ownerId}/users")
    public List<User> getOwnerUsers(@PathVariable long ownerId) {

        Owner theOwner = ownerService.findById(ownerId);

        if (theOwner == null) {
            throw new MyEntityNotFoundException("Owner id not found - " + ownerId);
        }

        return theOwner.getUsers();
    }


}











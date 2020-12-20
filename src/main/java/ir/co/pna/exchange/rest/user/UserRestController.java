package ir.co.pna.exchange.rest.user;

import ir.co.pna.exchange.entity.NormalContract;
import ir.co.pna.exchange.entity.PublicOwner;
import ir.co.pna.exchange.entity.Subcontract;
import ir.co.pna.exchange.entity.User;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import ir.co.pna.exchange.service.normalContract.NormalContractService;
import ir.co.pna.exchange.service.subcontract.SubcontractService;
import ir.co.pna.exchange.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class UserRestController {

	private UserService userService;
	private NormalContractService normalContractService;
	private SubcontractService subcontractService;

	@Autowired
	public UserRestController(
			UserService theUserService,
			NormalContractService theNormalContractService,
			SubcontractService theSubcontractService
	) {
		userService = theUserService;
		normalContractService = theNormalContractService;
		subcontractService = theSubcontractService;
	}
	
	@GetMapping("/users")
	public List<User> findAll() {
		return userService.findAll();
	}


	@GetMapping("/users/{userId}")
	public User getUser(@PathVariable String userId) {
		
		return userService.findById(userId);
	}
	

	@PostMapping("/users")
	public User addUser(@RequestBody User theUser) {

		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update

		theUser.setId(null);

		userService.save(theUser);

		return theUser;
	}

	@PutMapping("/users")
	public User updateUser(@RequestBody User theUser) {

		userService.save(theUser);

		return theUser;
	}

	// more queries ............................................................

	@GetMapping("/users/{userId}/publicowners")
	public List<PublicOwner> getPublicOwners(@PathVariable String userId) {
		return userService.getPublicOwners(userId);
	}

	@GetMapping("/users/{userId}/publicowners/{ownerId}")
	public PublicOwner getUserPublicOwner(@PathVariable String userId, @PathVariable String ownerId) {
		return userService.getPublicOwner(userId, ownerId);
	}

	@GetMapping("/users/{userId}/publicowners/{ownerId}/innormalcontracts")
	public List<NormalContract> getOwnerInNormalContracts(@PathVariable String userId, @PathVariable String ownerId) {
		return userService.getPublicOwnerInNormalContracts(userId, ownerId);
	}


	@GetMapping("/users/{userId}/publicowners/{ownerId}/outnormalcontracts")
	public List<NormalContract> getPublicOwnerOutNormalContracts(@PathVariable String userId, @PathVariable String ownerId) {
		return userService.getPublicOwnerOutNormalContracts(userId, ownerId);
	}

	@GetMapping("/users/{userId}/publicowners/{ownerId}/innormalcontracts/{normalContractId}")
	public NormalContract getPublicOwnerInNormalContract(@PathVariable String userId, @PathVariable String ownerId, @PathVariable int normalContractId) {
		return userService.getPublicOwnerInNormalContract(userId, ownerId, normalContractId);
	}


	@GetMapping("/users/{userId}/publicowners/{ownerId}/outnormalcontracts/{normalContractId}")
	public NormalContract getOwnerOutNormalContract(@PathVariable String userId, @PathVariable String ownerId, @PathVariable int normalContractId) {
		return userService.getPublicOwnerOutNormalContract(userId, ownerId, normalContractId);
	}

	@GetMapping("/users/{userId}/publicowners/{ownerId}/insubcontracts")
	public List<Subcontract> getPublicOwnerInSubcontracts(@PathVariable String userId, @PathVariable String ownerId) {
		return userService.getPublicOwnerInSubcontracts(userId, ownerId);
	}

	@GetMapping("/users/{userId}/publicowners/{ownerId}/insubcontracts/{subcontractId}")
	public Subcontract getPublicOwnerInSubcontract(@PathVariable String userId, @PathVariable String ownerId, @PathVariable int subcontractId) {
		return userService.getPublicOwnerInSubcontract(userId, ownerId, subcontractId);
	}

	@GetMapping("/users/{userId}/publicowners/{ownerId}/innormalcontracts/{normalContractId}/subcontracts")
	public List<Subcontract> getPublicOwnerInNormalContractSubcontracts(@PathVariable String userId, @PathVariable String ownerId, @PathVariable int normalContractId) {
		return userService.getPublicOwnerInNormalContractSubcontracts(userId, ownerId, normalContractId);
	}

	@GetMapping("/users/{userId}/publicowners/{ownerId}/innormalcontracts/{normalContractId}/subcontracts/{subcontractId}")
	public Subcontract getPublicOwnerInNormalContractSubcontract(@PathVariable String userId, @PathVariable String ownerId, @PathVariable int normalContractId, @PathVariable int subcontractId) {
		return userService.getPublicOwnerInNormalContractSubcontract(userId, ownerId, normalContractId, subcontractId);
	}

	@PutMapping("/users/{userId}/publicowners/{ownerId}/innormalcontracts/{normalContractId}")
	public NormalContract updatePublicOwnerInNormalContract(@PathVariable String userId, @PathVariable String ownerId, @PathVariable int normalContractId, @RequestBody Map<String, Object> payload) {
		NormalContract theNormalContract = userService.getPublicOwnerInNormalContract(userId, ownerId, normalContractId);
		return normalContractService.update(theNormalContract, payload);
	}
//
	@PutMapping("/users/{userId}/publicowners/{ownerId}/outnormalcontracts/{normalContractId}")
	public NormalContract updatePublicOwnerOutNormalContract(@PathVariable String userId, @PathVariable String ownerId, @PathVariable int normalContractId, @RequestBody Map<String, Object> payload) {
		NormalContract theNormalContract = userService.getPublicOwnerOutNormalContract(userId, ownerId, normalContractId);
		return normalContractService.update(theNormalContract, payload);
	}

	@PutMapping("/users/{userId}/publicowners/{ownerId}/insubcontracts/{subcontractId}")
	public Subcontract updatePublicOwnerInSubcontract(@PathVariable String userId, @PathVariable String ownerId, @PathVariable int subcontractId, @RequestBody Map<String, Object> payload) {
		Subcontract theSubcontract = userService.getPublicOwnerInSubcontract(userId, ownerId, subcontractId);
		return subcontractService.update(theSubcontract, payload);
	}

	@PutMapping("/users/{userId}/publicowners/{ownerId}/innormalcontracts/{normalContractId}/subcontracts/{subcontractId}")
	public Subcontract updatePublicOwnerInNormalContractSubcontract(@PathVariable String userId, @PathVariable String ownerId, @PathVariable int normalContractId, @PathVariable int subcontractId, @RequestBody Map<String, Object> payload) {
		Subcontract theSubcontract = userService.getPublicOwnerInNormalContractSubcontract(userId, ownerId, normalContractId, subcontractId);
		return subcontractService.update(theSubcontract, payload);
	}

	@PutMapping("/users/{userId}/publicowners/{ownerId}/outnormalcontracts/{normalContractId}/charge")
	public NormalContract chargePublicOwnerOutNormalContract(@PathVariable String userId, @PathVariable String ownerId, @PathVariable int normalContractId, @RequestBody Map<String, Object> payload) {
		NormalContract theNormalContract = userService.getPublicOwnerOutNormalContract(userId, ownerId, normalContractId);
		payload.put("operator_national_code", userId);
		return normalContractService.charge(theNormalContract, payload);
	}

	@PutMapping("/users/{userId}/publicowners/{ownerId}/outnormalcontracts/{normalContractId}/claim")
	public NormalContract claimPublicOwnerOutNormalContract(@PathVariable String userId, @PathVariable String ownerId, @PathVariable int normalContractId, @RequestBody Map<String, Object> payload) {
		NormalContract theNormalContract = userService.getPublicOwnerOutNormalContract(userId, ownerId, normalContractId);
		payload.put("operator_national_code", userId);
		return normalContractService.claim(theNormalContract, payload);
	}

	@PutMapping("/users/{userId}/publicowners/{ownerId}/innormalcontracts/{normalContractId}/subcontracts/{subcontractId}/pay")
	public Subcontract payPublicOwnerInNormalContractSubcontract(@PathVariable String userId, @PathVariable String ownerId, @PathVariable int normalContractId, @PathVariable int subcontractId, @RequestBody Map<String, Object> payload) {
		Subcontract theSubcontract = userService.getPublicOwnerInNormalContractSubcontract(userId, ownerId, normalContractId, subcontractId);
		payload.put("operator_national_code", userId);
		return subcontractService.pay(theSubcontract, payload);
	}


	@PostMapping("/users/{userId}/publicowners/{ownerId}/outnormalcontracts" )
	public NormalContract addNormalContract(@PathVariable String userId, @PathVariable String ownerId, @RequestBody Map<String, Object> payload) {
		PublicOwner thePublicOwner = userService.getPublicOwner(userId, ownerId);
		if (thePublicOwner == null){
			throw new MyEntityNotFoundException("public owner id not found - " + ownerId);
		}
		return normalContractService.save(payload);
	}

	@PostMapping("/users/{userId}/publicowners/{ownerId}/innormalcontracts/{normalContractId}/subcontracts" )
	public Subcontract addSubcontract(@PathVariable String userId, @PathVariable String ownerId, @PathVariable int normalContractId, @RequestBody Map<String, Object> payload) {
		NormalContract theNormalContract = userService.getPublicOwnerInNormalContract(userId, ownerId, normalContractId);
		if (theNormalContract == null){
			throw new MyEntityNotFoundException("in normal contract id not found - " + normalContractId);
		}
		return subcontractService.save(payload);
	}
}











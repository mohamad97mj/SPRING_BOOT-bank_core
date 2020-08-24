package ir.co.pna.exchange.rest.User;

import ir.co.pna.exchange.entity.Owner;
import ir.co.pna.exchange.entity.User;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import ir.co.pna.exchange.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserRestController {

	private UserService userService;
	
	@Autowired
	public UserRestController(UserService theUserService) {
		userService = theUserService;
	}
	
	@GetMapping("/users")
	public List<User> findAll() {
		return userService.findAll();
	}


	@GetMapping("/users/{userId}")
	public User getUser(@PathVariable long userId) {
		
		User theUser = userService.findById(userId);
		
		if (theUser == null) {
			throw new MyEntityNotFoundException("User id not found - " + userId);
		}
		
		return theUser;
	}
	

	@PostMapping("/users")
	public User addUser(@RequestBody User theUser) {
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
		
		theUser.setNationalCode(0);
		
		userService.save(theUser);
		
		return theUser;
	}
	
	// add mapping for PUT /Users - update existing User
	
	@PutMapping("/users")
	public User updateUser(@RequestBody User theUser) {
		
		userService.save(theUser);
		
		return theUser;
	}
	

	@DeleteMapping("/users/{userId}")
	public String deleteUser(@PathVariable int userId) {
		
		User tempUser = userService.findById(userId);
		
		// throw exception if null
		
		if (tempUser == null) {
			throw new MyEntityNotFoundException("User id not found - " + userId);
		}
		
		userService.deleteById(userId);
		
		return "Deleted user id - " + userId;
	}

	// more queries ............................................................

	@GetMapping("/users/{userId}/owners")
	public List<Owner> getOwnerUsers(@PathVariable int userId) {

		User theUser = userService.findById(userId);

		if (theUser == null) {
			throw new MyEntityNotFoundException("Owner id not found - " + userId);
		}

		return theUser.getOwners();
	}
	
}











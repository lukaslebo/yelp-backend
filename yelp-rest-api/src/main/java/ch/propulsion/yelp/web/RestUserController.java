package ch.propulsion.yelp.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import ch.propulsion.yelp.domain.JsonViews;
import ch.propulsion.yelp.domain.User;
import ch.propulsion.yelp.service.UserService;

@RestController
@RequestMapping( "/api/users" )
public class RestUserController {
	
	private final UserService userService;
	
	@Autowired
	public RestUserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	@JsonView( JsonViews.Summary.class )
	public List<User> getAllUsers() {
		return this.userService.findAll();
	}
	
	@GetMapping( "/{id}" )
	@JsonView( JsonViews.Detail.class )
	public User getUser(@PathVariable Long id) {
		return this.userService.findById(id);
	}
	
	@PostMapping( "/sign_up" )
	@JsonView( JsonViews.Detail.class )
	public User createUser(@RequestBody Map<String, String> json) {
		String firstName = json.get("firstName");
		String lastName = json.get("lastName");
		String email = json.get("email");
		String password = json.get("password");
		if (firstName == null || lastName == null || email == null || password == null) {
			return null;
		}
		User user = new User(firstName, lastName, email, password);
		return this.userService.save(user);
	}
	
	@PutMapping( "/{id}" )
	@JsonView( JsonViews.Detail.class )
	public User updateUser(@RequestBody Map<String, String> json, @PathVariable Long id) {
		String firstName = json.get("firstName");
		String lastName = json.get("lastName");
		String email = json.get("email");
		String password = json.get("password");
		User user = new User(id, firstName, lastName, email, password);
		return this.userService.updateUser(user);
	}
	
	@DeleteMapping( "/{id}" )
	@ResponseStatus( value = HttpStatus.NO_CONTENT )
	@JsonView( JsonViews.Detail.class )
	public void deleteUser(@PathVariable Long id) {
		this.userService.deleteUserById(id);
	}
	
}

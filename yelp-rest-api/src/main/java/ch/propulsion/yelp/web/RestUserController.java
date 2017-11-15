package ch.propulsion.yelp.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import ch.propulsion.yelp.security.JwtUtil;
import ch.propulsion.yelp.security.service.JwtAuthenticationResponse;
import ch.propulsion.yelp.service.UserService;

@RestController
@RequestMapping( "/api/users" )
public class RestUserController {
	
	private final String tokenHeader = "Authorization";
	private final UserService userService;
	private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
	
	@Autowired
	public RestUserController(UserService userService, UserDetailsService userDetailsService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.userDetailsService = userDetailsService;
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
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
	@JsonView( JsonViews.ReviewListInUser.class )
	public User updateUser(@RequestBody Map<String, String> json, @PathVariable String id, HttpServletRequest request) {
		String firstName = json.get("firstName");
		String lastName = json.get("lastName");
		String email = json.get("email");
		String password = json.get("password");
		User user = new User(id, firstName, lastName, email, password);
		if (isAuthenticated(request, id)) {
			return this.userService.updateUser(user);
        }
		return null;
	}
	
	@DeleteMapping( "/{id}" )
	@ResponseStatus( value = HttpStatus.NO_CONTENT )
	public void deleteUser(@PathVariable String id, HttpServletRequest request) {
        if (isAuthenticated(request, id)) {
        		this.userService.deleteUserById(id);        	
        }
	}
	
	
	@PostMapping( "/sign_in" )
	public ResponseEntity<?> login(@RequestBody Map<String, String> json, Device device) {
		String email = json.get("email");
		String password = json.get("password");
		System.out.println("login attempt with email: " + email);
		System.out.println("and password: " + password);
		// Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String token = jwtUtil.generateToken(userDetails, device);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}
	
	
}

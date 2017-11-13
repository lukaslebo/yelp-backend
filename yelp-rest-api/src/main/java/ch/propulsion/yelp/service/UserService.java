package ch.propulsion.yelp.service;

import java.util.List;

import ch.propulsion.yelp.domain.User;

public interface UserService {
	
	User save(User user);
	User findById(Long id);
	List<User> findAll();
	User findByEmail(String email);
	User updateUser(User user);
	void deleteUserById(Long id);

}

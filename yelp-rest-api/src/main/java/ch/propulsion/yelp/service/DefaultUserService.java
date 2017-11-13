package ch.propulsion.yelp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.propulsion.yelp.domain.User;
import ch.propulsion.yelp.repository.UserRepository;

@Service
@Transactional
public class DefaultUserService implements UserService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public DefaultUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public User save(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public User findById(Long id) {
		return this.userRepository.findById(id);
	}

	@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	@Override
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

	@Override
	public User updateUser(User user) {
		Long id = user.getId();
		if (id == null) {
			return null;
		}
		User userFromRepo =  this.userRepository.findById(id);
		if (userFromRepo == null) {
			return null;
		}
		if (user.getEmail() != null) {
			userFromRepo.setEmail(user.getEmail());
		}
		if (user.getFirstName() != null) {
			userFromRepo.setFirstName(user.getFirstName());
		}
		if (user.getLastName() != null) {
			userFromRepo.setLastName(user.getLastName());
		}
		if (user.getPassword() != null) {
			userFromRepo.setPassword(user.getPassword());
		}
		return this.userRepository.findById(id);
	}

	@Override
	public void deleteUserById(Long id) {
		this.userRepository.deleteById(id);
	}

}

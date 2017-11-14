package ch.propulsion.yelp.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ch.propulsion.yelp.repository.UserRepository;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public JwtUserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByEmail(username);
	}

}

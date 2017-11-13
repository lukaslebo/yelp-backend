package ch.propulsion.yelp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.propulsion.yelp.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	User findById(Long id);
	User findByEmail(String email);
	void deleteById(Long id);
	
}

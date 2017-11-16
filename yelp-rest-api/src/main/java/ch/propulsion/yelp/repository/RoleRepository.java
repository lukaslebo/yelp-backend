package ch.propulsion.yelp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.propulsion.yelp.domain.Role;

public interface RoleRepository extends JpaRepository<Role, String>{
	
	Role findByName(String name);
	
}

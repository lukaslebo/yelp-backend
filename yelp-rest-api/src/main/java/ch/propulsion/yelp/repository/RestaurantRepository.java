package ch.propulsion.yelp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.propulsion.yelp.domain.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
	
	Restaurant findById(String id);
	List<Restaurant> findByName(String name);
	List<Restaurant> findByNameIgnoreCaseContaining(String name);
	void deleteById(String id);
	
}

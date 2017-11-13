package ch.propulsion.yelp.service;

import java.util.List;

import ch.propulsion.yelp.domain.Restaurant;

public interface RestaurantService {
	
	Restaurant findById(Long id);
	List<Restaurant> findAll();
	List<Restaurant> findByName(String name);
	List<Restaurant> findByNameIgnoreCaseContaining(String name);
	
}

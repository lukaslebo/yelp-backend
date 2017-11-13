package ch.propulsion.yelp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ch.propulsion.yelp.domain.Restaurant;
import ch.propulsion.yelp.repository.RestaurantRepository;

public class DefaultRestaurantService implements RestaurantService {
	
	private final RestaurantRepository restaurantRepository;
	
	@Autowired
	public DefaultRestaurantService(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}
	
	@Override
	public Restaurant findById(Long id) {
		return this.restaurantRepository.findById(id);
	}

	@Override
	public List<Restaurant> findAll() {
		return this.restaurantRepository.findAll();
	}

	@Override
	public List<Restaurant> findByName(String name) {
		return this.restaurantRepository.findByName(name);
	}

	@Override
	public List<Restaurant> findByNameIgnoreCaseContaining(String name) {
		return this.restaurantRepository.findByNameIgnoreCaseContaining(name);
	}

}

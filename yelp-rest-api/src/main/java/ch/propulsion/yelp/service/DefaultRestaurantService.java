package ch.propulsion.yelp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.propulsion.yelp.domain.Restaurant;
import ch.propulsion.yelp.repository.RestaurantRepository;

@Service
@Transactional
public class DefaultRestaurantService implements RestaurantService {
	
	private final RestaurantRepository restaurantRepository;
	private final ReviewService reviewService;
	
	@Autowired
	public DefaultRestaurantService(RestaurantRepository restaurantRepository, ReviewService reviewService) {
		this.restaurantRepository = restaurantRepository;
		this.reviewService = reviewService;
	}
	
	@Override
	public Restaurant findById(String id) {
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

	@Override
	public Restaurant saveRestaurant(Restaurant restaurant) {
		return this.restaurantRepository.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurant(Restaurant restaurant) {
		String id = restaurant.getId();
		if (id == null) {
			return null;
		}
		Restaurant restaurantFromRepo =  this.restaurantRepository.findById(id);
		if (restaurantFromRepo == null) {
			return null;
		}
		if (restaurant.getName() != null) {
			restaurantFromRepo.setName(restaurant.getName());
		}
		if (restaurant.getAddress() != null) {
			restaurantFromRepo.setAddress(restaurant.getAddress());
		}
		if (restaurant.getEmail() != null) {
			restaurantFromRepo.setEmail(restaurant.getEmail());
		}
		if (restaurant.getPhone() != null) {
			restaurantFromRepo.setPhone(restaurant.getPhone());
		}
		if (restaurant.getLogo() != null) {
			restaurantFromRepo.setLogo(restaurant.getLogo());
		}
		if (restaurant.getUrl() != null) {
			restaurantFromRepo.setUrl(restaurant.getUrl());
		}
		return this.restaurantRepository.findById(id);
	}

	@Override
	public void deleteById(String id) {
		Restaurant restaurant = this.restaurantRepository.findById(id);
		while ( restaurant.getReviews().size() > 0) {
			String review_id = restaurant.getReviews().get(0).getId();
			this.reviewService.deleteReviewById(review_id);		
		}
		this.restaurantRepository.deleteById(id);
	}

}

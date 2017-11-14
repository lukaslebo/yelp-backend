package ch.propulsion.yelp.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import ch.propulsion.yelp.domain.JsonViews;
import ch.propulsion.yelp.domain.Restaurant;
import ch.propulsion.yelp.domain.Review;
import ch.propulsion.yelp.domain.User;
import ch.propulsion.yelp.service.RestaurantService;
import ch.propulsion.yelp.service.ReviewService;

@RestController
@RequestMapping( "/api" ) 
public class RestRestaurantController {
	
	private final RestaurantService restaurantService;
	private final ReviewService reviewService;
	
	@Autowired
	public RestRestaurantController(RestaurantService restaurantService, ReviewService reviewService) {
		this.restaurantService = restaurantService;
		this.reviewService = reviewService;
	}
	
	@GetMapping( "s" )
	@JsonView( JsonViews.Summary.class )
	public List<Restaurant> getAllRestaurants() {
		return this.restaurantService.findAll();
	}
	
	@GetMapping( "s/{id}" )
	@JsonView( JsonViews.Summary.class )
	public Restaurant getRestaurant(@PathVariable Long id) {
		return this.restaurantService.findById(id);
	}
	
	@PostMapping( "/restaurant/{restaurantId}/review" ) 
	@JsonView( JsonViews.Summary.class )
	public Review postReview(@RequestBody Map<String, String> json, @PathVariable Long restaurantId) {
		String text = json.get("text");
		Integer rating = Integer.parseInt(json.get("rating"));
		User user = new User();
		Restaurant restaurant = this.restaurantService.findById(restaurantId);
		Review review = new Review(text, rating, user, restaurant);
		return this.reviewService.saveReview(review);
	}
	
	@PutMapping( "/restaurant/{restaurantId}/review/{reviewId}" )
	@JsonView( JsonViews.Summary.class )
	public Review updateReview(@RequestBody Map<String, String> json, @PathVariable Long restaurantId, @PathVariable Long reviewId) {
		Review reviewFromRepo = this.reviewService.findByID(reviewId);
		String text = json.get("text");
		Integer rating = Integer.parseInt(json.get("rating"));
		if (text != null) {
			reviewFromRepo.setText(text);
		}
		if (rating != null) {
			reviewFromRepo.setRating(rating);
		}
		return this.reviewService.findByID(reviewId);
	}
	
	@DeleteMapping( "/restaurant/{restaurantId}/review/{reviewId}" )
	@JsonView( JsonViews.Summary.class )
	public void deleteReview(@PathVariable Long restaurantId, @PathVariable Long reviewId) {
		this.reviewService.deleteReviewById(reviewId);
	}
	
}

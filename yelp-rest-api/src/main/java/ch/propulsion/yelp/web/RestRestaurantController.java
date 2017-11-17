package ch.propulsion.yelp.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import ch.propulsion.yelp.domain.JsonViews;
import ch.propulsion.yelp.domain.Restaurant;
import ch.propulsion.yelp.domain.Review;
import ch.propulsion.yelp.domain.User;
import ch.propulsion.yelp.security.JwtUtil;
import ch.propulsion.yelp.service.RestaurantService;
import ch.propulsion.yelp.service.ReviewService;
import ch.propulsion.yelp.service.UserService;

@RestController
@RequestMapping( "/api" ) 
public class RestRestaurantController {
	
	private final static String tokenHeader = "Authorization";
	private final RestaurantService restaurantService;
	private final ReviewService reviewService;
	private final UserService userService;
	private final JwtUtil jwtUtil;
	
	@Autowired
	public RestRestaurantController(RestaurantService restaurantService, ReviewService reviewService, UserService userService, JwtUtil jwtUtil) {
		this.restaurantService = restaurantService;
		this.reviewService = reviewService;
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
	private boolean isAuthenticated(HttpServletRequest request, String id) {
		String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtUtil.getUsernameFromToken(token);
        User user = this.userService.findByUserName(username);
        if (user.getId().equals(id)) {
        		return true;
        }
        return false;
	}
	
	@GetMapping( "/restaurants" )
	@JsonView( JsonViews.ReviewListInRestaurant.class )
	public List<Restaurant> getAllRestaurants() {
		return this.restaurantService.findAll();
	}
	
	@GetMapping( "/restaurants/{id}" )
	@JsonView( JsonViews.ReviewListInRestaurant.class )
	public Restaurant getRestaurant(@PathVariable String id) {
		return this.restaurantService.findById(id);
	}
	
	@GetMapping( "/restaurants/search" )
	@JsonView( JsonViews.ReviewListInRestaurant.class )
	public List<Restaurant> searchRestaurantsFancy(@RequestParam( value = "q", required = true ) String name) {
		return this.restaurantService.findByNameIgnoreCaseContaining(name);
	}
	
	@PostMapping( "/restaurant/{restaurantId}/review" ) 
	@JsonView( JsonViews.ReviewDetails.class )
	public Review postReview(@RequestBody Map<String, String> json, @PathVariable String restaurantId, HttpServletRequest request) {
		String text = json.get("text");
		Integer rating = Integer.parseInt(json.get("rating"));
		String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtUtil.getUsernameFromToken(token);
        User user = this.userService.findByUserName(username);
		Restaurant restaurant = this.restaurantService.findById(restaurantId);
		if (restaurant == null) {
			return null;
		}
		Review review = new Review(text, rating, user, restaurant);
		return this.reviewService.saveReview(review);
	}
	
	@PutMapping( "/restaurant/{restaurantId}/review/{reviewId}" )
	@JsonView( JsonViews.ReviewDetails.class )
	public Review updateReview(@RequestBody Map<String, String> json, @PathVariable String restaurantId, @PathVariable String reviewId, HttpServletRequest request) {
		Review reviewFromRepo = this.reviewService.findByID(reviewId);
		String text = json.get("text");
		Integer rating = Integer.parseInt(json.get("rating"));
		String id = reviewFromRepo.getUser().getId();
		if (isAuthenticated(request, id)) {
			if (text != null) {
				reviewFromRepo.setText(text);
			}
			if (rating != null) {
				reviewFromRepo.setRating(rating);
			}
			return this.reviewService.findByID(reviewId);
        }
		return null;
	}
	
	@DeleteMapping( "/restaurant/{restaurantId}/review/{reviewId}" )
	public void deleteReview(@PathVariable String restaurantId, @PathVariable String reviewId, HttpServletRequest request) {
		String id = this.reviewService.findByID(reviewId).getUser().getId();
		if (isAuthenticated(request, id)) {
			this.reviewService.deleteReviewById(reviewId);
        }
	}
	
}

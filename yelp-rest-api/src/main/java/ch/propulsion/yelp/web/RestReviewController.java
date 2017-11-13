package ch.propulsion.yelp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import ch.propulsion.yelp.domain.JsonViews;
import ch.propulsion.yelp.domain.Review;
import ch.propulsion.yelp.service.ReviewService;

@RestController
@RequestMapping( "/api/review" ) 
public class RestReviewController {
	
	private final ReviewService reviewService;
	
	@Autowired
	public RestReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	@GetMapping
	@JsonView( JsonViews.Summary.class )
	public List<Review> getAllReviews() {
		return this.reviewService.findAll();
	}
	
	@GetMapping( "/{id}" )
	@JsonView( JsonViews.Summary.class )
	public Review getReview(@PathVariable Long id) {
		return this.reviewService.findByID(id);
	}
	
}

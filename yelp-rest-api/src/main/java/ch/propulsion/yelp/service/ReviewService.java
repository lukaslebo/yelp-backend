package ch.propulsion.yelp.service;

import java.util.List;

import ch.propulsion.yelp.domain.Review;

public interface ReviewService {
	
	Review saveReview(Review review);
	Review findByID(String id);
	List<Review> findAll();
	Review updateReview(Review review);
	void deleteReviewById(String id);
	
}

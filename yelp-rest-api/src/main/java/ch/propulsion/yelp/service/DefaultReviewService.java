package ch.propulsion.yelp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ch.propulsion.yelp.domain.Review;
import ch.propulsion.yelp.repository.ReviewRepository;

public class DefaultReviewService implements ReviewService {
	
	private final ReviewRepository reviewRepository;
	
	@Autowired
	public DefaultReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@Override
	public Review saveReview(Review review) {
		return this.reviewRepository.save(review);
	}

	@Override
	public Review findByID(Long id) {
		return this.reviewRepository.findById(id);
	}

	@Override
	public List<Review> findAll() {
		return this.reviewRepository.findAll();
	}

	@Override
	public Review updateReview(Review review) {
		Long id = review.getId();
		if (id == null) {
			return null;
		}
		Review reviewFromRepo = this.reviewRepository.findById(id);
		if (review.getText() != null) {
			reviewFromRepo.setText(review.getText());
		}
		return this.reviewRepository.findById(id);
	}

	@Override
	public void deleteReviewById(Long id) {
		this.reviewRepository.deleteById(id);
	}

}

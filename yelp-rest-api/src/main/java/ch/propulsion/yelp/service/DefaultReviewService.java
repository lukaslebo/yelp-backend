package ch.propulsion.yelp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.propulsion.yelp.domain.Review;
import ch.propulsion.yelp.repository.ReviewRepository;

@Service
@Transactional
public class DefaultReviewService implements ReviewService {
	
	private final ReviewRepository reviewRepository;
	
	@Autowired
	public DefaultReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@Override
	public Review saveReview(Review review) {
		Review savedReview =  this.reviewRepository.save(review);
		review.getUser().addReview(savedReview);
		review.getRestaurant().addReview(savedReview);
		return savedReview;
	}

	@Override
	public Review findByID(String id) {
		return this.reviewRepository.findById(id);
	}

	@Override
	public List<Review> findAll() {
		return this.reviewRepository.findAll();
	}

	@Override
	public Review updateReview(Review review) {
		String id = review.getId();
		if (id == null) {
			return null;
		}
		Review reviewFromRepo = this.reviewRepository.findById(id);
		if (reviewFromRepo == null) {
			return null;
		}
		if (review.getText() != null) {
			reviewFromRepo.setText(review.getText());
		}
		if (review.getRating() != null) {
			reviewFromRepo.setRating(review.getRating());
		}
		return this.reviewRepository.findById(id);
	}

	@Override
	public void deleteReviewById(String id) {
		Review review = this.reviewRepository.findById(id);
		review.getUser().removeReview(review);
		review.getRestaurant().removeReview(review);
		this.reviewRepository.deleteById(id);
	}

}

package ch.propulsion.yelp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.propulsion.yelp.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String>{
	
	Review findById(String id);
	List<Review> findByRating(Integer rating);
	void deleteById(String id);
	
}

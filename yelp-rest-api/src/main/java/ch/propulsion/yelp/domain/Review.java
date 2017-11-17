package ch.propulsion.yelp.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table( name = "reviews" )
@Data
@NoArgsConstructor
@EqualsAndHashCode( exclude = { "id" } )
@ToString( exclude = { "user", "restaurant" } )
public class Review {
	
	@Id
	@Setter( AccessLevel.PRIVATE )
	@JsonView( JsonViews.Summary.class )
	private String id;
	
	@JsonView( JsonViews.Summary.class )
	@Column( nullable = false )
	private String text;
	
	@JsonView( JsonViews.Summary.class )
	@Column( nullable = false )
	private Integer rating;
	
	@JsonView( JsonViews.Summary.class )
	@Column( name = "date_created", updatable = false, nullable = false )
	private LocalDateTime dateCreated = LocalDateTime.now();
	
	@JsonView( value = {JsonViews.ReviewListInRestaurant.class, JsonViews.ReviewDetails.class} )
	@ManyToOne
	private User user;
	
	@JsonView( value = {JsonViews.ReviewListInUser.class, JsonViews.ReviewDetails.class} )
	@ManyToOne
	private Restaurant restaurant;

	public Review(String id, String text, Integer rating, User user, Restaurant restaurant, LocalDateTime dateCreated) {
		this.id = id;
		this.text = text;
		this.rating = rating;
		this.user = user;
		this.restaurant = restaurant;
		this.dateCreated = dateCreated;
	}
	
	public Review(String text, Integer rating, User user, Restaurant restaurant) {
		this(null, text, rating, user, restaurant, LocalDateTime.now());
	}
	
	@PrePersist
	public void onCreate() {
		String uuid = UUID.randomUUID().toString();
		setId(uuid);
	}
	
}

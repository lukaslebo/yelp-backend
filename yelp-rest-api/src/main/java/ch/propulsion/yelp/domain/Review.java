package ch.propulsion.yelp.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "reviews" )
@Data
@NoArgsConstructor
@EqualsAndHashCode( exclude = { "id" } )
public class Review {
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@JsonView( JsonViews.Summary.class )
	private Long id;
	
	@JsonView( JsonViews.Summary.class )
	@Column( nullable = false )
	private String text;
	
	@JsonView( JsonViews.Summary.class )
	@Column( nullable = false )
	private Integer rating;
	
	@JsonView( JsonViews.Summary.class )
	@Column( name = "date_created", updatable = false, nullable = false )
	private LocalDateTime dateCreated = LocalDateTime.now();
	
	@JsonView( JsonViews.Summary.class )
	@OneToOne
	private User user;
	
	@JsonView( JsonViews.Summary.class )
	@ManyToOne
	private Restaurant restaurant;

	public Review(Long id, String text, Integer rating, User user, Restaurant restaurant) {
		this.id = id;
		this.text = text;
		this.rating = rating;
		this.user = user;
		this.restaurant = restaurant;
	}
	
	public Review(String text, Integer rating, User user, Restaurant restaurant) {
		this(null, text, rating, user, restaurant);
	}
	
}

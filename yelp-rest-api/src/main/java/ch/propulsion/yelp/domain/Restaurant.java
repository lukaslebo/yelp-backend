package ch.propulsion.yelp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table( name = "restaurants" )
@Data
@NoArgsConstructor
@EqualsAndHashCode( exclude = { "id" } )
public class Restaurant {
	
	@Id
	@Setter( AccessLevel.PRIVATE )
	@JsonView( JsonViews.Summary.class )
	private String id;
	
	@JsonView( JsonViews.Summary.class )
	@Column( nullable = false )
	private String name;
	
	@JsonView( JsonViews.Summary.class )
	private String address;
	
	@JsonView( JsonViews.Summary.class )
	private String email;
	
	@JsonView( JsonViews.Summary.class )
	private String phone;
	
	@JsonView( JsonViews.Summary.class )
	private String logo;
	
	@JsonView( JsonViews.Summary.class )
	@Column( nullable = false )
	private String url;
	
	@JsonView( JsonViews.ReviewListInRestaurant.class )
	@OneToMany( mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	private List<Review> reviews = new ArrayList<>();

	public Restaurant(String id, String name, String address, String email, String phone, String logo, String url) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.logo = logo;
		this.url = url;
	}
	
	public Restaurant(String name, String address, String email, String phone, String logo, String url) {
		this(null, name, address, email, phone, logo, url);
	}
	
	public void addReview(Review review) {
		this.reviews.add(review);
	}
	
	@PrePersist
	public void onCreate() {
		String uuid = UUID.randomUUID().toString();
		setId(uuid);
	}

}

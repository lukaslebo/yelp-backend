package ch.propulsion.yelp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table( name = "users" )
@Data
@NoArgsConstructor
@EqualsAndHashCode( exclude = { "id" } )
@ToString( exclude = { "password" } )
public class User {
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@JsonView( JsonViews.Summary.class )
	private Long id;
	
	@JsonView( JsonViews.Summary.class )
	@Column( name = "first_name", nullable = false, length = 50 )
	private String firstName;
	
	@JsonView( JsonViews.Summary.class )
	@Column( name = "last_name", nullable = false, length = 50 )
	private String lastName;
	
	@JsonView( JsonViews.Summary.class )
	@Column( nullable = false )
	private String email;
	
	@Column( nullable = false )
	private String password;
	
	@JsonView( JsonViews.Detail.class )
	@OneToMany( mappedBy = "user", cascade = CascadeType.REMOVE )
	private List<Review> reviews = new ArrayList<>();

	public User(Long id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
	
	public User(String firstName, String lastName, String email, String password) {
		this(null, firstName, lastName, email, password);
	}
	
}

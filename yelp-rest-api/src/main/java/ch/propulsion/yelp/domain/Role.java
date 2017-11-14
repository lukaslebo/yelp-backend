package ch.propulsion.yelp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "roles" )
@Data
@EqualsAndHashCode( exclude = { "id" } )
@NoArgsConstructor
public class Role {
	
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "role_seq" )
	@SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 25)
	private Long id;

	@Column( unique = true, nullable = false, length = 50 )
	private String name;

}

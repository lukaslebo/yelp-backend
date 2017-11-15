package ch.propulsion.yelp.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
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
	private String id;

	@Column( unique = true, nullable = false, length = 50 )
	private String name;
	
	public Role(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public Role(String name) {
		this(null, name);
	}
	
	@PrePersist
	public void onCreate() {
		String uuid = UUID.randomUUID().toString();
		setId(uuid);
	}
	
}

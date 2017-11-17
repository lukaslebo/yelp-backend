package ch.propulsion.yelp.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;

import ch.propulsion.yelp.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table( name = "users" )
@Data
@NoArgsConstructor
@EqualsAndHashCode( exclude = { "id" } )
@ToString( exclude = { "password" } )
@Component
public class User implements UserDetails {
	
	private static final long serialVersionUID = 2365563617740595474L;
	
	private static RoleRepository roleRepository;

	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		User.roleRepository = roleRepository;
	}
	
	@Id
	@Setter( AccessLevel.PRIVATE )
	@JsonView( JsonViews.Summary.class )
	private String id;
	
	@JsonView( JsonViews.Summary.class )
	@Column( name = "first_name", nullable = false, length = 50 )
	private String firstName;
	
	@JsonView( JsonViews.Summary.class )
	@Column( name = "last_name", nullable = false, length = 50 )
	private String lastName;
	
	@JsonView( JsonViews.Summary.class )
	@Column( unique = true, nullable = false )
	private String email;
	
	private String username;
	
	@Column( nullable = false, length = 100 )
	private String password;
	
	@JsonView( JsonViews.ReviewListInUser.class )
	@OneToMany( mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	private List<Review> reviews = new ArrayList<>();
	
	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable( name = "user_roles" )
	private Set<Role> roles = new HashSet<>();

	public User(String id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = email;
        this.password = password;
        this.roles.add(roleRepository.findByName(Role.ROLE_USER));
    }
	
	public User(String firstName, String lastName, String email, String password) {
		this(null, firstName, lastName, email, password);
	}
	
	public void addReview(Review review) {
		this.reviews.add(review);
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}
	
	public void removeReview(Review review) {
		int index = this.reviews.indexOf(review);
		if ( index != -1 ) {
			this.reviews.remove(index);			
		}
	}
	
	public void removeRole(Role role) {
		this.roles.remove(role);
	}
	
	@PrePersist
	public void onCreate() {
		String uuid = UUID.randomUUID().toString();
		setId(uuid);
	}
	
	// -- Spring Security: UserDetails -----------------------------------------

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (getRoles() == null) {
			return Collections.emptySet();
		}
		return getRoles().stream()
				.map(Role::getName)
				.map(String::toUpperCase)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
	}

	@Override
	public boolean isAccountNonExpired() {
		return isEnabled();
	}

	@Override
	public boolean isAccountNonLocked() {
		return isEnabled();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isEnabled();
	}

	@Override
	public boolean isEnabled() {
		// We don't have a property to enable/disable the user - always return true
		return true;
	}
	
}

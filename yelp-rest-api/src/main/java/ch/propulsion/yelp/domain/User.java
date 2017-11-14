package ch.propulsion.yelp.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "user_seq" )
	@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 25)
	@Setter( AccessLevel.NONE )
	@JsonView( JsonViews.Summary.class )
	private Long id;
	
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
	
	@JsonView( JsonViews.Detail.class )
	@OneToMany( mappedBy = "user", cascade = CascadeType.ALL )
	private List<Review> reviews = new ArrayList<>();
	
	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable( name = "user_roles" ) // , joinColumns = {@JoinColumn( name = "user_id", referencedColumnName = "id" )}, inverseJoinColumns = {@JoinColumn( name = "access_level_id", referencedColumnName = "id" )}
	private Set<Role> roles = new HashSet<>();

	public User(Long id, String firstName, String lastName, String email, String password) {
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
		return true;
	}
	
}

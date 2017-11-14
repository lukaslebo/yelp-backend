package ch.propulsion.yelp.security;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtAuthenticationRequest implements Serializable {

	private static final long serialVersionUID = 5435116658875574447L;

    private String username;
    private String password;

    public JwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
    
}

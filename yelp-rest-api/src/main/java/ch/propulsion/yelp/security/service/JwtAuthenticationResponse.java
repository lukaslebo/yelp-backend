package ch.propulsion.yelp.security.service;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 2221278925259599433L;

	private final String token;
	
	public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
	
}

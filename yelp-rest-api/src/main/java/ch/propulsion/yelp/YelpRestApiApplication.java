package ch.propulsion.yelp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class YelpRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(YelpRestApiApplication.class, args);
	}
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
}

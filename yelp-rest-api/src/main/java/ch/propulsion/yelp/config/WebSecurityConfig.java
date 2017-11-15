package ch.propulsion.yelp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ch.propulsion.yelp.security.JwtAuthenticationEntryPoint;
import ch.propulsion.yelp.security.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }
    
    @Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
	}

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	
	    	httpSecurity.authorizeRequests().antMatchers("/").permitAll().and()
			.authorizeRequests().antMatchers("/h2_console/**").permitAll();
    	
        httpSecurity
        
	        // we don't need CSRF because our token is invulnerable
	        .csrf().disable()
	        
	        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	        
	        // don't create session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            
//            .authorizeRequests()
//	            .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()//allow CORS option calls
//	            .antMatchers("/resources/**").permitAll()
//	            .anyRequest().authenticated()
//	            .and()
            
            .mvcMatcher("/**")
	            .authorizeRequests()
					.mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					.and()
            
            .mvcMatcher("/api/users/**")
				.authorizeRequests()
					.mvcMatchers(HttpMethod.POST, "/api/users/sign_in").permitAll()
					.mvcMatchers(HttpMethod.POST, "/api/users/sign_up").permitAll()
					.mvcMatchers(HttpMethod.GET, "/api/users").hasRole("USER")
					.mvcMatchers("/api/users/**").hasRole("USER")
					.and()
	
			.mvcMatcher("/api/review/**")
				.authorizeRequests()
					.mvcMatchers(HttpMethod.GET,  "/api/review/**").hasRole("USER")
					.and()

			.mvcMatcher("/api/restaurants/**")
				.authorizeRequests()
					.mvcMatchers(HttpMethod.GET, "/api/restaurants").permitAll()
					.mvcMatchers(HttpMethod.GET, "/api/restaurants/**").permitAll()
					.and()
			
			.mvcMatcher("/api/restaurant/**")
				.authorizeRequests()
					.mvcMatchers("/api/restaurant/**").hasRole("USER")
					.and()

			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS, "/", "/**").permitAll()
			.antMatchers(
	                    HttpMethod.GET,
	                    "/",
	                    "/*.html",
	                    "/favicon.ico",
	                    "/**/*.html",
	                    "/**/*.css",
	                    "/**/*.js"
	                ).permitAll()
	                .antMatchers("/auth/**").permitAll()
	                .anyRequest().authenticated()
	                .and()
		
	        .mvcMatcher("/**")
				.authorizeRequests()
					.mvcMatchers("/**").denyAll();
                

        // Custom JWT based security filter
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        httpSecurity.headers().cacheControl();
        
        httpSecurity.headers().frameOptions().disable();
        
    }
}
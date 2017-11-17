package ch.propulsion.yelp.dbUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import ch.propulsion.yelp.domain.Restaurant;
import ch.propulsion.yelp.domain.Review;
import ch.propulsion.yelp.domain.Role;
import ch.propulsion.yelp.domain.User;
import ch.propulsion.yelp.repository.RestaurantRepository;
import ch.propulsion.yelp.repository.ReviewRepository;
import ch.propulsion.yelp.repository.RoleRepository;
import ch.propulsion.yelp.repository.UserRepository;

@Component
public class DataBaseLoader implements ApplicationRunner {
	
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;
	private final RestaurantRepository restaurantRepository;
	private final RoleRepository roleRepository;
	private final  BCryptPasswordEncoder bcrypt;
	
	@Autowired
	public DataBaseLoader(UserRepository userRepository, ReviewRepository reviewRepository, RestaurantRepository restaurantRepository, RoleRepository roleRepository, BCryptPasswordEncoder bcrypt) {
		this.userRepository = userRepository;
		this.reviewRepository = reviewRepository;
		this.restaurantRepository = restaurantRepository;
		this.roleRepository = roleRepository;
		this.bcrypt = bcrypt;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Role ROLE_USER = new Role(Role.ROLE_USER);
		Role ROLE_ADMIN = new Role(Role.ROLE_ADMIN);
		if ( null == roleRepository.findByName(ROLE_USER.getName()) ) {
			ROLE_USER = roleRepository.save(ROLE_USER);			
		}
		if ( null == roleRepository.findByName(ROLE_ADMIN.getName()) ) {
			ROLE_ADMIN = roleRepository.save(ROLE_ADMIN);
		}
		

		User[] userArr = new User[6];
		userArr[0] = new User("Lukas", "Lebovitz", "fake11@mail.com", "password11");
		userArr[0].addRole(ROLE_ADMIN);
		userArr[1] = new User("Michal", "Michal Zurczak", "fake8@mail.com", "password8");
		userArr[2] = new User("Jeremy", "Savor", "fake4@mail.com", "password4");
		userArr[3] = new User("Kim", "Beyeler", "fake7@mail.com", "password7");
		userArr[4] = new User("Fabio", "Wanner", "fake2@mail.com", "password2");
		userArr[5] = new User("Laurent", "Hoxhaj", "fake16@mail.com", "password16");
		
		for (User user : userArr) {
			if ( null == userRepository.findByEmail(user.getEmail()) ) {
				user.setPassword(bcrypt.encode(user.getPassword()));
				userRepository.save(user);				
			}
		}
		
		
		Restaurant[] restaurantArr = new Restaurant[3];
		restaurantArr[0] = new Restaurant("Santa Lucia", "Theaterstrasse 10, 8001 Zürich", null, "+41 44 261 80 70", "https://www.bindella.ch/upload/prj/restaurant/logo/santa-lucia-neu-corso@2x1.png", "https://www.bindella.ch/de/santa-lucia-corso.html");
		restaurantArr[1] = new Restaurant("Iroquois", "Seefeldstrasse 120, 8008 Zürich", "iroquois@iroquois.ch", null, "http://www.iroquois.ch/typo3conf/ext/twospice/Resources/Public/Images/iroquois/logo.png", "http://www.iroquois.ch");
		restaurantArr[2] = new Restaurant("Storchen", "Weinplatz 2, 8001 Zürich", "info@storchen.ch", "+41 44 227 27 27", "http://www.faulhaber-marketing.ch/images/clients/storchen/Storchen_Zurich_Logo2017_gross.jpg", "https://storchen.ch/de/restaurants-bars/");
		
		for ( Restaurant restaurant : restaurantArr) {
			if ( 0 == restaurantRepository.findByName(restaurant.getName()).size() ) {
				restaurantRepository.save(restaurant);
			}
		}
		
		String text = "";
		Review[] reviewArr = new Review[1];
		text = "I always get the oricchetti with gorgonzola and for me the small portion is quite enough, but for the bigger hunger they also great pizzas!";
		reviewArr[0] = new Review(text, 5, userRepository.findByEmail("fake7@mail.com"), restaurantRepository.findByName("Santa Lucia").get(0));
		
		for (Review review : reviewArr) {
			if ( 0 == reviewRepository.findAll().size() ) {
				Review savedReview = reviewRepository.save(review);
				review.getUser().addReview(savedReview);
				review.getRestaurant().addReview(savedReview);
			}
		}
		
		
	}
	
}

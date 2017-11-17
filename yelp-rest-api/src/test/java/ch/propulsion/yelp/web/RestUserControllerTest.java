package ch.propulsion.yelp.web;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.CoreMatchers.hasItems;
//import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
//import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ch.propulsion.yelp.repository.UserRepository;

@RunWith( SpringRunner.class )
@SpringBootTest( webEnvironment = WebEnvironment.MOCK )
@AutoConfigureMockMvc( print = MockMvcPrint.SYSTEM_ERR )
@Transactional
public class RestUserControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	public void test() throws Exception {
		mockMvc.perform(get("/api/users").accept(APPLICATION_JSON))//
			.andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))//
			.andExpect(status().isOk());//
//			.andExpect(jsonPath("$[1]").exists())//
			// last names of users whose first name starts with "J"
//			.andExpect(jsonPath("$[?(@.firstName =~ /J.+/)].lastName", hasItems("Lebovitz")));
	}
	
}

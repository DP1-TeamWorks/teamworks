package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.samples.petclinic.config.TestWebConfig;
import org.springframework.samples.petclinic.configuration.GenericIdToEntityConverter;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.samples.petclinic.validation.TeamValidator;
import org.springframework.samples.petclinic.validation.UserValidator;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = AuthController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
@WebAppConfiguration
@ContextConfiguration(classes = {TestWebConfig.class, SecurityConfiguration.class})
@Import(AuthController.class)
public class AuthControllerTest {
	
	private static final int TEST_USER_ID = 26;
	private static final int TEST_TEAM_ID = 26;

	@MockBean
    GenericIdToEntityConverter idToEntityConverter;
	
	@MockBean
	private UserTWService userTWService;
	
	@MockBean
	private TeamService teamService;
	
	@MockBean
	private UserValidator userValidator;
	
	@MockBean
	private TeamValidator teamValidator;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	protected MockHttpSession mockSession;

	private UserTW user;
	private Team team;
	
	@BeforeEach
	void setup() {
			
		user = new UserTW();
		user.setId(TEST_USER_ID);
		user.setName("Paco");
		user.setLastname("Martin");
		user.setEmail("andrespuertas@cyber");
		user.setPassword("123123123");
		user.setRole(Role.team_owner);
		
		team=new Team();
		team.setId(TEST_TEAM_ID);
		user.setTeam(team);
		team.setIdentifier("OOF");
		
		mockSession.setAttribute("userId",TEST_USER_ID);
		mockSession.setAttribute("teamId",TEST_TEAM_ID);
		
		given(this.userTWService.getLoginUser(user.getEmail(), user.getPassword())).willReturn(user);
		given(this.userValidator.validate(user, errors);
	}
	
	
	//arreglar
	@Test
	void testLogin() throws Exception{
		String json = objectMapper.writeValueAsString(user);
		
		mockMvc.perform(post("/api/auth/login").session(mockSession)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isAccepted());
	}
	
	
	@Test
	void testLogout() throws Exception{
		mockMvc.perform(delete("/api/auth/logout").session(mockSession))
		.andExpect(status().isOk());
	}
	
	
	//validators??
	@Test
	void testSignUp() throws Exception{
		String json = objectMapper.writeValueAsString(user);
		
		mockMvc.perform(post("/api/auth/signup").session(mockSession)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isOk());
	}
	
	
	
	

}

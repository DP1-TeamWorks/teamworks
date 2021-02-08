package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessResourceFailureException;
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
import org.springframework.validation.BindingResult;

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
	private BindingResult bindingResult;
	
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
		user.setEmail("pacomartin@cyber");
		user.setPassword("123123123");
		user.setRole(Role.team_owner);
		
		
		team=new Team();
		team.setId(TEST_TEAM_ID);
		user.setTeam(team);
		team.setIdentifier("cyber");
		team.setName("cyber");
		
		mockSession.setAttribute("userId",TEST_USER_ID);
		mockSession.setAttribute("teamId",TEST_TEAM_ID);
		
		
		
		given(this.userTWService.getLoginUser(user.getEmail(), user.getPassword())).willReturn(user);
		
	}
	

	@Test
	void testLogin() throws Exception{
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("teamname", "cyber");
		map.put("identifier", "cyber");
		map.put("mail", "pacomartin@cyber");
		map.put("username", "paco");
		map.put("lastname", "martin");
		map.put("password", "123123123");
		String json = objectMapper.writeValueAsString(map);
		
		mockMvc.perform(post("/api/auth/login").session(mockSession)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isAccepted());
	}
	
	@Test
	void testLoginNull() throws Exception{
		doThrow(new DataAccessResourceFailureException("User Null"))
		.when(userTWService).getLoginUser("pacomartin@cyber", "123123123");

		String json = objectMapper.writeValueAsString(null);
		
		mockMvc.perform(post("/api/auth/login").session(mockSession)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void testLoginWithErrors() throws Exception{
		
		Map<String, String> map = new HashMap<String, String>();
		String json = objectMapper.writeValueAsString(map);
		
		mockMvc.perform(post("/api/auth/login").session(mockSession)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isBadRequest());
	}
	
	
	@Test
	void testLogout() throws Exception{
		mockMvc.perform(delete("/api/auth/logout").session(mockSession))
		.andExpect(status().isOk());
	}
	
	

	@Test
	void testSignUp() throws Exception{
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("teamname", "cyber");
		map.put("identifier", "cyber");
		map.put("username", "paco");
		map.put("lastname", "martin");
		map.put("password", "123123123");
		String json = objectMapper.writeValueAsString(map);
		
		mockMvc.perform(post("/api/auth/signup").session(mockSession)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isOk());
	}
	

	
	@Test
	void testSignUpException() throws Exception{
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("teamname", "cyber");
		map.put("identifier", "cyber");
		map.put("username", "Paco");
		map.put("lastname", "Martin");
		map.put("password", "123123123");
		String json = objectMapper.writeValueAsString(map);

		Team teami=new Team();
		teami.setIdentifier("cyber");
		teami.setName("cyber");
		
		
		doThrow(new DataAccessResourceFailureException("Error guardando el equipo"))
		.when(teamService).saveTeam(teami);

		
		mockMvc.perform(post("/api/auth/signup").session(mockSession)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isBadRequest());
	}
	

}

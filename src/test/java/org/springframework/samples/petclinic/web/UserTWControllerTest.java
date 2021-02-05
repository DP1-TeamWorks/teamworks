package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class UserTWControllerTest {
	@InjectMocks
	UserTWController userTWController;
	
	private static final int TEST_USER_ID = 1;
	private static final int TEST_TEAM_ID = 3;
	@MockBean
	private UserTWService UserTWService;
        
    @MockBean
	private TeamService teamService;
    private UserTW george;
	
	private Team team;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	protected MockHttpSession mockSession;
	@Autowired
	private MockMvc mockMvc;
	@BeforeEach
	void setup() {
		
		george = new UserTW();
		george.setId(TEST_USER_ID);
		george.setName("George");
		george.setLastname("Franklin");
		george.setEmail("andrespuertas@cyber");
		george.setPassword("123456789");
		george.setRole(Role.employee);
		team=new Team();
		george.setTeam(team);
		mockSession.setAttribute("userId",TEST_USER_ID);
		mockSession.setAttribute("teamId",TEST_TEAM_ID);
		given(this.UserTWService.findUserById(TEST_USER_ID)).willReturn(george);
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(team);
		

	}

	
    @Test
	void testInitCreationForm() throws Exception {
		String georgejson = objectMapper.writeValueAsString(george);
		mockMvc.perform(post("/api/userTW").session(mockSession).content(georgejson)).andExpect(status().is(200));
	}
}

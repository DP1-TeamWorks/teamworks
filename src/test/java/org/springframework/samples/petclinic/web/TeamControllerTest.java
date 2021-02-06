package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.samples.petclinic.config.TestWebConfig;
import org.springframework.samples.petclinic.configuration.GenericIdToEntityConverter;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = TeamController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
@WebAppConfiguration
@ContextConfiguration(classes = {TestWebConfig.class, SecurityConfiguration.class})
@Import(TeamController.class)
public class TeamControllerTest {
	
	private static final int TEST_TEAM_ID = 12;

	@MockBean
    GenericIdToEntityConverter idToEntityConverter;
	
	@MockBean
	private TeamService teamService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	protected MockHttpSession mockSession;

	private Team atomatic;

	@BeforeEach
	void setup() {
		atomatic = new Team();
		atomatic.setId(TEST_TEAM_ID);
		atomatic.setName("Atomatic");
		atomatic.setIdentifier("ATOM");
		mockSession.setAttribute("teamId",TEST_TEAM_ID);
		
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(atomatic);		
	}
	
	@Test
	void testGetTeamName() throws Exception {
		String atomaticjson = objectMapper.writeValueAsString(atomatic);
		mockMvc.perform(get("/api/team").session(mockSession))
		.andExpect(status().isOk());
		//.andExpect(content().json(atomaticjson));
		
	}
}

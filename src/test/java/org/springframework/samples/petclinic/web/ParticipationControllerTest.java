package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;

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
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.enums.Role;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.ParticipationService;
import org.springframework.samples.petclinic.service.ProjectService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ParticipationController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
@WebAppConfiguration
@ContextConfiguration(classes = {TestWebConfig.class, SecurityConfiguration.class})
@Import(ParticipationController.class)
public class ParticipationControllerTest {

	private static final int TEST_USER_ID = 1;
	private static final int TEST_PARTICIPATION_ID = 6;
	private static final int TEST_PROJECT_ID = 3;
	private static final int TEST_DEPARTMENT_ID = 5;
	private static final int TEST_TEAM_ID = 4;

	@MockBean
    GenericIdToEntityConverter idToEntityConverter;
	@MockBean
	private ProjectService projectService;
	@MockBean
	private ParticipationService participationService;
	@MockBean
	private UserTWService userTWService;
	@MockBean
	private BelongsService belongsService;

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	protected MockHttpSession mockSession;
	private UserTW juan;
	private Collection<Participation> participationCol;
	private UserTW rosa;
	private Project game;

	private Participation participation;
	private Department calidad;
	private Belongs belongsJuan;
	private Belongs belongsRosa;
	private Team team;

	@BeforeEach
	void setup() {
		//Team
		team=new Team();
		team.setName("NK");
		team.setId(TEST_TEAM_ID);
		//Calidad

		calidad=new Department();
		calidad.setId(TEST_DEPARTMENT_ID);
		calidad.setName("Calidad");
		calidad.setDescription("Me aseguro de...");
		calidad.setTeam(team);

		//Juan
		juan=new UserTW();
		juan.setId(TEST_USER_ID);
		juan.setName("Juan");
		juan.setLastname("Franklin");
		juan.setEmail("andrespuertas@cyber");
		juan.setPassword("123456789");
		juan.setRole(Role.team_owner);
		juan.setTeam(team);
		//juan belong
		belongsJuan=new Belongs();
		belongsJuan.setUserTW(juan);
		belongsJuan.setIsDepartmentManager(true);
		belongsJuan.setDepartment(calidad);

		rosa=new UserTW();
		rosa.setId(TEST_PARTICIPATION_ID);
		rosa.setName("Rosa");
		rosa.setLastname("Fabra");
		rosa.setEmail("andrespuertas@cyber");
		rosa.setPassword("123456789");
		rosa.setRole(Role.employee);
		rosa.setTeam(team);

		//Rosa belongs
		belongsRosa=new Belongs();
		belongsRosa.setDepartment(calidad);
		belongsRosa.setUserTW(rosa);
		belongsRosa.setIsDepartmentManager(false);

		game=new Project();
		game.setId(TEST_PROJECT_ID);
		game.setName("Game");
		game.setDescription("Hola");
		game.setDepartment(calidad);


		participation=new Participation();
		participation.setUserTW(juan);
		participation.setProject(game);
		participation.setIsProjectManager(true);
		participationCol=new ArrayList<>();
		participationCol.add(participation);
		//Session
		mockSession.setAttribute("userId",TEST_USER_ID);
		mockSession.setAttribute("teamId",TEST_TEAM_ID);
		given(userTWService.findUserById(TEST_USER_ID)).willReturn(juan);
		given(userTWService.findUserById(TEST_PARTICIPATION_ID)).willReturn(rosa);
		given(projectService.findProjectById(TEST_PROJECT_ID)).willReturn(game);
		given(participationService.findCurrentParticipation(TEST_USER_ID, TEST_PROJECT_ID)).willReturn(participation);
		given(belongsService.findCurrentBelongs(TEST_USER_ID, TEST_DEPARTMENT_ID)).willReturn(belongsJuan);
		given(belongsService.findCurrentBelongs(TEST_PARTICIPATION_ID, TEST_DEPARTMENT_ID)).willReturn(belongsRosa);
		given(participationService.findCurrentProjectManager(TEST_PROJECT_ID)).willReturn(participation);



	}

	@Test
	void testGetParticipationsFromProject() throws Exception {
		given(participationService.findCurrentParticipationsInProject(TEST_PROJECT_ID)).willReturn(participationCol);
		String participationJson=objectMapper.writeValueAsString(participationCol);
		String projectId=String.valueOf(TEST_PROJECT_ID);

		mockMvc.perform(get("/api/projects/participation").session(mockSession).param("projectId", projectId)).andExpect(status().is(200)).andExpect(content().json(participationJson));
	}
	@Test
	void testCreateParticipations() throws Exception {

		String projectId=String.valueOf(TEST_PROJECT_ID);
		String participationUserId=String.valueOf(TEST_PARTICIPATION_ID);
		mockMvc.perform(post("/api/projects/participation/create").session(mockSession).param("participationUserId", participationUserId).param("projectId", projectId)).andExpect(status().is(200));
	}
	@Test
	void testCreateParticipationsDontBelongProjectDepartment() throws Exception {
		//Eliminamos al usuario del departamento
		given(belongsService.findCurrentBelongs(TEST_PARTICIPATION_ID, TEST_DEPARTMENT_ID)).willReturn(null);
		String projectId=String.valueOf(TEST_PROJECT_ID);
		String participationUserId=String.valueOf(TEST_PARTICIPATION_ID);
		mockMvc.perform(post("/api/projects/participation/create").session(mockSession).param("participationUserId", participationUserId).param("projectId", projectId)).andExpect(status().is(400));
	}
	@Test
	void testCreateParticipationsOtherTeamUser() throws Exception {
		//Metemos la id de un usuario de otro equipo
		UserTW lola=new UserTW();
		Team teamLola=new Team();
		team.setId(3);
		lola.setTeam(teamLola);
		given(userTWService.findUserById(TEST_USER_ID)).willReturn(lola);
		String projectId=String.valueOf(TEST_PROJECT_ID);
		String participationUserId=String.valueOf(TEST_PARTICIPATION_ID);
		mockMvc.perform(post("/api/projects/participation/create").session(mockSession).param("participationUserId", participationUserId).param("projectId", projectId)).andExpect(status().is(400));
	}
	@Test
	void testCreateParticipationsWithExistingParticipation() throws Exception {
		//Creamos una participacion
		Participation par=new Participation();
		par.setProject(game);
		par.setUserTW(rosa);
		par.setIsProjectManager(false);
		given(participationService.findCurrentParticipation(TEST_PARTICIPATION_ID, TEST_PROJECT_ID)).willReturn(par);
		String projectId=String.valueOf(TEST_PROJECT_ID);
		String participationUserId=String.valueOf(TEST_PARTICIPATION_ID);
		mockMvc.perform(post("/api/projects/participation/create").session(mockSession).param("participationUserId", participationUserId).param("projectId", projectId)).andExpect(status().is(400));
	}
	@Test
	void testEditParticipations() throws Exception {
		//Quitar juan de project manager
		String projectId=String.valueOf(TEST_PROJECT_ID);
		String participationUserId=String.valueOf(TEST_USER_ID);
		mockMvc.perform(post("/api/projects/participation/create").session(mockSession).param("participationUserId", participationUserId).param("projectId", projectId).param("willBeProjectManager", "false")).andExpect(status().is(200));
	}
	@Test
	void testCreateParticipationsChangeProjectManager() throws Exception {
		Participation par=new Participation();
		par.setProject(game);
		par.setUserTW(rosa);
		par.setIsProjectManager(false);
		given(participationService.findCurrentParticipation(TEST_PARTICIPATION_ID, TEST_PROJECT_ID)).willReturn(par);
		String projectId=String.valueOf(TEST_PROJECT_ID);
		String participationUserId=String.valueOf(TEST_PARTICIPATION_ID);
		mockMvc.perform(post("/api/projects/participation/create").session(mockSession).param("participationUserId", participationUserId).param("projectId", projectId).param("willBeProjectManager", "true")).andExpect(status().is(200));
	}
	@Test
	void testCreateParticipationsProjectManagerWithoutPermison() throws Exception {
		//Eliminamos los permisos de juan
		juan.setRole(Role.employee);
		participation.setIsProjectManager(false);
		given(userTWService.findUserById(TEST_USER_ID)).willReturn(juan);
		given(participationService.findCurrentParticipation(TEST_USER_ID, TEST_PROJECT_ID)).willReturn(participation);
		given(belongsService.findCurrentBelongs(TEST_USER_ID, TEST_DEPARTMENT_ID)).willReturn(null);
		String projectId=String.valueOf(TEST_PROJECT_ID);
		String participationUserId=String.valueOf(TEST_PARTICIPATION_ID);
		mockMvc.perform(post("/api/projects/participation/create").session(mockSession).param("participationUserId", participationUserId).param("projectId", projectId).param("willBeProjectManager", "true")).andExpect(status().is(400));
	}

	@Test
	void testDeleteParticipations() throws Exception {

		String projectId=String.valueOf(TEST_PROJECT_ID);
		String participationUserId=String.valueOf(TEST_USER_ID);
		mockMvc.perform(delete("/api/projects/participation/delete").session(mockSession).param("participationUserId", participationUserId).param("projectId", projectId)).andExpect(status().is(200));
	}
	@Test
	void testDeleteNotExistingParticipations() throws Exception {
		//Le pasamos una id invalida
		String projectId=String.valueOf(TEST_PROJECT_ID);
		String participationUserId=String.valueOf(TEST_PARTICIPATION_ID);
		mockMvc.perform(delete("/api/projects/participation/delete").session(mockSession).param("participationUserId", participationUserId).param("projectId", projectId)).andExpect(status().is(400));
	}

}

package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.DepartmentService;
import org.springframework.samples.petclinic.service.ParticipationService;
import org.springframework.samples.petclinic.service.ProjectService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ProjectController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
@WebAppConfiguration
@ContextConfiguration(classes = {TestWebConfig.class, SecurityConfiguration.class})
@Import(ProjectController.class)
public class ProjectControllerTest {
	
	private static final int TEST_PROJECT_ID = 11;
	private static final int TEST_DEPARTMENT_ID = 54;
	private static final int TEST_PARTICIPATION_ID = 44;
	private static final int TEST_USER_ID = 33;
	private static final int TEST_BELONGS_ID = 22;
	@MockBean
    GenericIdToEntityConverter idToEntityConverter;
	
	@MockBean
	private ProjectService projectService;
	
	@MockBean
	private DepartmentService departmentService;
	
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

	private Project passdp;
	private Department LSI;
	private Participation work;
	private UserTW pablo;
	private Belongs working;

	@BeforeEach
	void setup() {
		passdp = new Project();
		passdp.setId(TEST_PROJECT_ID);
		passdp.setName("APROBAR");
		passdp.setDescription("A tope chavales");
		passdp.setCreationTimestamp(LocalDate.now());
		mockSession.setAttribute("projectId",TEST_PROJECT_ID);
		
		LSI = new Department();
		passdp.setDepartment(LSI);
		mockSession.setAttribute("departmentId",TEST_DEPARTMENT_ID);
		given(this.projectService.findProjectById(TEST_PROJECT_ID)).willReturn(passdp);
		given(this.departmentService.findDepartmentById(TEST_DEPARTMENT_ID)).willReturn(LSI);
		
		work = new Participation();
		List<Participation> works = new ArrayList<>();
		works.add(work);
		passdp.setParticipations(works);
		mockSession.setAttribute("participationId",TEST_PARTICIPATION_ID);
		given(this.participationService.findParticipationById(TEST_PARTICIPATION_ID)).willReturn(work);
		
		pablo = new UserTW();
		pablo.setRole(Role.team_owner);
		mockSession.setAttribute("userId",TEST_USER_ID);
		given(this.userTWService.findUserById(TEST_USER_ID)).willReturn(pablo);
		
		working = new Belongs();
		mockSession.setAttribute("belongsId",TEST_BELONGS_ID);
		given(this.belongsService.findCurrentBelongs(TEST_USER_ID, TEST_DEPARTMENT_ID)).willReturn(working);
		
	}
	
	//TODO 
	//Creo que tiene que ver con tema de login que tienes que ser teamowner o algo por el estilo, es lo unico que se me ocurre
	//Lo segundo es que me falte algun atributo por dar, que he dado los "minimos"
	
	@Test
	void getProyects() throws Exception{
		mockMvc.perform(get("/api/projects").session(mockSession))
		.andExpect(status().isOk());
	}
	
	@Test
	void getProyect() throws Exception{
		mockMvc.perform(get("/api/project/details").session(mockSession))
		.andExpect(status().isOk())
		.andExpect(content().string("APROBAR"));
	}
}
	
/*	@Test
	void testGetTeamName() throws Exception {
		mockMvc.perform(get("/api/team").session(mockSession))
		.andExpect(status().isOk())
		.andExpect(status().is(200))
		.andExpect(content().string("Atomatic"));
	}
	
	@Test
	void testUpdateTeam() throws Exception {
		atomatic.setName("AtomaticUpdated");
		atomatic.setIdentifier("ATOMUPDATED");
		String atomaticupdated = objectMapper.writeValueAsString(atomatic);
		mockMvc.perform(post("/api/team").session(mockSession).contentType(MediaType.APPLICATION_JSON).content(atomaticupdated))
		.andExpect(status().isOk())
		.andExpect(status().is(200));	
		
		mockMvc.perform(get("/api/team").session(mockSession))
		.andExpect(status().is(200))
		.andExpect(content().string("AtomaticUpdated"));
	}
	
	@Test
	void testUpdateTeamNull() throws Exception {
		atomatic.setName(null);
		//atomatic.setIdentifier("ATOMUPDATED");
		String atomaticupdated = objectMapper.writeValueAsString(atomatic);
		mockMvc.perform(post("/api/team").session(mockSession).contentType(MediaType.APPLICATION_JSON).content(atomaticupdated))
		.andExpect(status().isOk())
		.andExpect(status().is(200));	
		
		mockMvc.perform(get("/api/team").session(mockSession))
		.andExpect(status().is(200))
		.andExpect(content().string("AtomaticUpdated"));
	}
	
	@Test
	void testGetTeamNameUpdated() throws Exception {
		mockMvc.perform(get("/api/team").session(mockSession))
		.andExpect(content().string("Atomatic"));
	}
}
*/
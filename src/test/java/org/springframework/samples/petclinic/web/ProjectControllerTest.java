package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.DepartmentService;
import org.springframework.samples.petclinic.service.ParticipationService;
import org.springframework.samples.petclinic.service.ProjectService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.samples.petclinic.validation.ProjectValidator;
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
	
	@MockBean
	private ProjectValidator projectValidator;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	protected MockHttpSession mockSession;

	private Project project;
	private Department department;
	private Participation part;
	private UserTW user;
	private UserTW user2;
	private Belongs belongs;
	private List<Project> projects;
	private List<Participation> participations;
	private List<Belongs> bel;
	private List<UserTW> users;
	private Map<String, Object> map;
	
	@BeforeEach
	void setup() {
		//proyecto
		project = new Project();
		project.setId(TEST_PROJECT_ID);
		project.setCreationTimestamp(LocalDate.now());
		project.setDescription("YeahPerdonen");
		project.setName("DragonBallRap");
		projects = new ArrayList<>();
		projects.add(project);

		
		//departamento
		department = new Department();
		department.setId(TEST_DEPARTMENT_ID);
		department.setName("Youtube");
		department.setDescription("Una descrip muy buena");
		department.setProjects(projects);
		//relacion con project
		project.setDepartment(department);
		
		
		//participacion
		part = new Participation();
		part.setId(TEST_PARTICIPATION_ID);
		part.setInitialDate(LocalDate.now());
		part.setIsProjectManager(true);
		participations = new ArrayList<>();
		//relacion con project
		part.setProject(project);
		participations.add(part);
		
		//user
		user = new UserTW();
		user.setId(TEST_USER_ID);
		user.setName("Pedro");
		user.setLastname("Sanz");
		user.setLastname("pedrosanz@cyber");
		user.setPassword("123123123");
		user.setRole(Role.team_owner);
		user.setParticipation(participations);
		users = new ArrayList<>();
		users.add(user);

		
		//relacion con participation
		part.setUserTW(user);

		//belongs
		belongs = new Belongs();
		belongs.setDepartment(department);
		belongs.setId(TEST_BELONGS_ID);
		belongs.setUserTW(user);
		belongs.setIsDepartmentManager(true);
		belongs.setInitialDate(LocalDate.now());
		bel = new ArrayList<>();
		bel.add(belongs);
		
		//relacion con  user y department
		user.setBelongs(bel);
		department.setBelongs(bel);
		
		
		mockSession.setAttribute("projectId",TEST_PROJECT_ID);
		mockSession.setAttribute("departmentId",TEST_DEPARTMENT_ID);
		mockSession.setAttribute("userId",TEST_USER_ID);
		mockSession.setAttribute("participationId", TEST_PARTICIPATION_ID);
		mockSession.setAttribute("belongsId", TEST_BELONGS_ID);
		
		
		given(this.userTWService.findUserById(TEST_USER_ID)).willReturn(user);
		given(this.belongsService.findCurrentBelongs(TEST_USER_ID, TEST_DEPARTMENT_ID)).willReturn(belongs);
		given(this.departmentService.findDepartmentById(TEST_DEPARTMENT_ID)).willReturn(department);
		given(this.projectService.findProjectById(TEST_PROJECT_ID)).willReturn(project);
		given(this.projectService.findUserProjects(TEST_PROJECT_ID)).willReturn(users);
		given(this.participationService.findMyDepartmentProjects(TEST_USER_ID, TEST_DEPARTMENT_ID)).willReturn(projects);
	}
	
	
	@Test
	void tesGetProyects() throws Exception{
	
		String json = objectMapper.writeValueAsString(projects);
		
		mockMvc.perform(get("/api/projects?departmentId={departmentId}", TEST_DEPARTMENT_ID)
				.session(mockSession))
		.andExpect(status().isOk())
		.andExpect(content().json(json));
	}
	
	@Test
	void tesGetProyectsForbidden() throws Exception{
		
		given(this.belongsService.findCurrentBelongs(TEST_USER_ID, TEST_DEPARTMENT_ID)).willReturn(null);
		user2 = new UserTW();
		user.setId(55555);
		user.setName("Ped");
		user.setLastname("Sa");
		user.setLastname("pedsa@cyber");
		user.setPassword("123123123");
		user.setRole(Role.employee);
		given(this.userTWService.findUserById(55555)).willReturn(user2);
		
		String json = objectMapper.writeValueAsString(projects);
		mockMvc.perform(get("/api/projects?departmentId={departmentId}", TEST_DEPARTMENT_ID)
				.session(mockSession))
		.andExpect(status().isForbidden());
	}
	
	
	@Test
	void testGetProyect() throws Exception{
		map = new HashMap<String, Object>();
		map.put("members", users);
		map.put("milestones", null);
		map.put("tags", null);
		
		String json = objectMapper.writeValueAsString(map);
		
		mockMvc.perform(get("/api/project/details?projectId={projectId}", TEST_PROJECT_ID)
				.session(mockSession))
		.andExpect(status().isOk())
		.andExpect(content().json(json));

	}
	
	void testGetProyectForbidden() throws Exception{
		
		given(this.belongsService.findCurrentBelongs(TEST_USER_ID, TEST_DEPARTMENT_ID)).willReturn(null);
		user2 = new UserTW();
		user.setId(55555);
		user.setName("Ped");
		user.setLastname("Sa");
		user.setLastname("pedsa@cyber");
		user.setPassword("123123123");
		user.setRole(Role.employee);
		given(this.userTWService.findUserById(55555)).willReturn(user2);
		
		
		map = new HashMap<String, Object>();
		map.put("members", users);
		map.put("milestones", null);
		map.put("tags", null);
		
		String json = objectMapper.writeValueAsString(map);
		
		mockMvc.perform(get("/api/project/details?projectId={projectId}", TEST_PROJECT_ID)
				.session(mockSession))
		.andExpect(status().isOk())
		.andExpect(content().json(json));

	}
	
	@Test
	void testGetMyProjects() throws Exception{
		String json = objectMapper.writeValueAsString(projects);
		
		mockMvc.perform(get("/api/projects/mine?departmentId={departmentId}", TEST_DEPARTMENT_ID)
				.session(mockSession))
		.andExpect(status().isOk())
		.andExpect(content().json(json));
	}
	
	@Test
	void testPostProject() throws Exception{
		String json = objectMapper.writeValueAsString(project);
		
		mockMvc.perform(post("/api/projects?departmentId={departmentId}", TEST_DEPARTMENT_ID)
				.session(mockSession).contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isOk());
	}
	
	@Test
	void testPostProjectException() throws Exception{
		doThrow(new DataAccessResourceFailureException("Id ya existente"))
		.when(projectService).saveProject(project);
		String json = objectMapper.writeValueAsString(project);
		
		mockMvc.perform(post("/api/projects?departmentId={departmentId}", TEST_DEPARTMENT_ID)
				.session(mockSession).contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void testUpdateProject() throws Exception{
		String json = objectMapper.writeValueAsString(project);
		
		mockMvc.perform(patch("/api/projects?departmentId={departmentId}&projectId={projectId}",
				TEST_DEPARTMENT_ID, TEST_PROJECT_ID)
				.session(mockSession).contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isOk());
	}
	
	
	@Test
	void testUpdateProjectException() throws Exception{
		doThrow(new DataAccessResourceFailureException("Id ya existente"))
		.when(projectService).saveProject(project);
		
		String json = objectMapper.writeValueAsString(project);
		
		mockMvc.perform(patch("/api/projects?departmentId={departmentId}&projectId={projectId}",
				TEST_DEPARTMENT_ID, TEST_PROJECT_ID)
				.session(mockSession).contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isBadRequest());
	}
	
	
	@Test
	void testDeleteProjects() throws Exception{
		
		mockMvc.perform(delete("/api/projects?departmentId={departmentId}&projectId={projectId}",
				TEST_DEPARTMENT_ID, TEST_PROJECT_ID)
				.session(mockSession))
		.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteProjectsNotFound() throws Exception{
		doThrow(new DataAccessResourceFailureException("Id no existente"))
		.when(projectService).deleteProjectById(TEST_PROJECT_ID);
		
		
		mockMvc.perform(delete("/api/projects?departmentId={departmentId}&projectId={projectId}",
				TEST_DEPARTMENT_ID, TEST_PROJECT_ID)
				.session(mockSession))
		.andExpect(status().isNotFound());
	}
	
	
	
}

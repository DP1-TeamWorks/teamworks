package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.doThrow;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

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
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.DepartmentService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = BelongsController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
@WebAppConfiguration
@ContextConfiguration(classes = {TestWebConfig.class, SecurityConfiguration.class})
@Import(BelongsController.class)
public class BelongsControllerTest {
	private static final int TEST_USER_ID = 1;
	private static final int TEST_BELONGUSER_ID = 6;
	private static final int TEST_TEAM_ID = 3;
	private static final int TEST_DEPARTMENT_ID = 5;
	
	@MockBean
    GenericIdToEntityConverter idToEntityConverter;

	@MockBean
	private UserTWService userTWService;
	@MockBean
	private  DepartmentService departmentService;
	@MockBean
	private BelongsService belongsService;
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	private UserTW juan;
	
	private UserTW rosa;
	
	private Team team;
	private Collection<Belongs> belongCol;
	
	private Department calidad;
	
	private Belongs belongs;
	@Autowired
	protected MockHttpSession mockSession;
	@BeforeEach
	void setup() {
		
		//Departaemnto Calidad
		calidad=new Department();
		calidad.setId(TEST_DEPARTMENT_ID);
		calidad.setName("Calidad");
		calidad.setDescription("Me aseguro de...");
		//Usuario Juan
		juan=new UserTW();
		juan.setId(TEST_USER_ID);
		juan.setName("Juan");
		juan.setLastname("Franklin");
		juan.setEmail("andrespuertas@cyber");
		juan.setPassword("123456789");
		juan.setRole(Role.team_owner);
		//juan belong
		belongs=new Belongs();
		belongs.setUserTW(juan);
		belongs.setIsDepartmentManager(true);
		belongs.setDepartment(calidad);
		//Usuario rosa
		rosa=new UserTW();
		rosa.setId(TEST_BELONGUSER_ID);
		rosa.setName("Rosa");
		rosa.setLastname("Fabra");
		rosa.setEmail("andrespuertas@cyber");
		rosa.setPassword("123456789");
		rosa.setRole(Role.employee);
		//Team
		team=new Team();
		team.setId(TEST_TEAM_ID);
		juan.setTeam(team);
		rosa.setTeam(team);
		calidad.setTeam(team);
		//Belongs col
		belongCol=new ArrayList<>();
		belongCol.add(belongs);
		//Session
		mockSession.setAttribute("userId",TEST_USER_ID);
		mockSession.setAttribute("teamId",TEST_TEAM_ID);
		
		given(belongsService.findCurrentBelongs(TEST_USER_ID, TEST_DEPARTMENT_ID)).willReturn(belongs);
		given(userTWService.findUserById(TEST_USER_ID)).willReturn(juan);
		given(userTWService.findUserById(TEST_BELONGUSER_ID)).willReturn(rosa);
		given(departmentService.findDepartmentById(TEST_DEPARTMENT_ID)).willReturn(calidad);
		given(belongsService.findCurrentBelongsInDepartment(TEST_DEPARTMENT_ID)).willReturn(belongCol);
	}
	
	@Test
	void testGetUsers() throws Exception {
		
		String belongsJson=objectMapper.writeValueAsString(belongCol);
		Integer departmentId=TEST_DEPARTMENT_ID;
		String departmentIdString=departmentId.toString();
		mockMvc.perform(get("/api/departments/belongs").session(mockSession).param("departmentId", departmentIdString)).andExpect(status().is(200)).andExpect(content().json(belongsJson));
	}
	
	@Test
	void testCreateBelongs() throws Exception {
		String departmentId=String.valueOf(TEST_DEPARTMENT_ID);
		String belonguserId=String.valueOf(TEST_BELONGUSER_ID);
		mockMvc.perform(post("/api/departments/belongs").session(mockSession).param("belongUserId", belonguserId).param("departmentId", departmentId)).andExpect(status().is(200));
	}
	
	@Test
	void testCreateBelongsWithExistingBelongs() throws Exception {
		//Añadimos un belong al usuario
		Belongs belongUser=new Belongs();
		belongUser.setUserTW(rosa);
		belongUser.setDepartment(calidad);
		belongUser.setIsDepartmentManager(false);
		given(belongsService.findCurrentBelongs(TEST_BELONGUSER_ID, TEST_DEPARTMENT_ID)).willReturn(belongUser);
		
		String departmentId=String.valueOf(TEST_DEPARTMENT_ID);
		String belonguserId=String.valueOf(TEST_BELONGUSER_ID);
		mockMvc.perform(post("/api/departments/belongs").session(mockSession).param("belongUserId", belonguserId).param("departmentId", departmentId)).andExpect(status().is(400));
	}
	
	@Test
	void testCreateBelongsWithOtherTeamUser() throws Exception {
		//Creamos el belong con un usuario de otro equipo
		Team team2=new Team();
		team2.setId(25);
		team2.setName("Nike");
		UserTW user=new UserTW();
		user.setId(TEST_BELONGUSER_ID+1);
		user.setName("Rosa2");
		user.setTeam(team2);
		given(userTWService.findUserById(TEST_BELONGUSER_ID+1)).willReturn(user);
		
		String departmentId=String.valueOf(TEST_DEPARTMENT_ID);
		String belonguserId=String.valueOf(TEST_BELONGUSER_ID+1);
		mockMvc.perform(post("/api/departments/belongs").session(mockSession).param("belongUserId", belonguserId).param("departmentId", departmentId)).andExpect(status().is(400));
	}
	
	@Test
	void testCreateBelongsWithOtherTeamDepartment() throws Exception {
		//Creamos el belong con un departamento de otro equipo
		Team java=new Team();
		java.setId(40);
		java.setName("Java");
		Department department=new Department();
		department.setId(TEST_DEPARTMENT_ID+1);
		department.setName("Tec");
		department.setTeam(java);
		
		given(departmentService.findDepartmentById(TEST_DEPARTMENT_ID+1)).willReturn(department);
		
		String departmentId=String.valueOf(TEST_DEPARTMENT_ID+1);
		String belonguserId=String.valueOf(TEST_BELONGUSER_ID);
		mockMvc.perform(post("/api/departments/belongs").session(mockSession).param("belongUserId", belonguserId).param("departmentId", departmentId)).andExpect(status().is(400));
	}
	
	@Test
	void testCreateBelongsGiveDepartmentManagerToOtherUser() throws Exception {
		given(belongsService.findCurrentDepartmentManager(TEST_DEPARTMENT_ID)).willReturn(belongs);
		String departmentId=String.valueOf(TEST_DEPARTMENT_ID);
		String belonguserId=String.valueOf(TEST_BELONGUSER_ID);
		mockMvc.perform(post("/api/departments/belongs").session(mockSession).param("belongUserId", belonguserId).param("departmentId", departmentId).param("isDepartmentManager", "true")).andExpect(status().is(200));
	}
	@Test
	void testEditBelongs() throws Exception {
		//Quitar juan de department manager
		String departmentId=String.valueOf(TEST_DEPARTMENT_ID);
		String belonguserId=String.valueOf(TEST_USER_ID);
		mockMvc.perform(post("/api/departments/belongs").session(mockSession).param("belongUserId", belonguserId).param("departmentId", departmentId).param("isDepartmentManager", "false")).andExpect(status().is(200));
	}
	@Test
	void testDeleteBelongs() throws Exception {
		
		String departmentId=String.valueOf(TEST_DEPARTMENT_ID);
		String belonguserId=String.valueOf(TEST_USER_ID);
		mockMvc.perform(delete("/api/departments/belongs").session(mockSession).param("belongUserId", belonguserId).param("departmentId", departmentId)).andExpect(status().is(200));
	}
	@Test
	void testDeleteNotExistingBelongs() throws Exception {
		//Introducimos la id de alguien que no tiene belongs
		String departmentId=String.valueOf(TEST_DEPARTMENT_ID);
		String belonguserId=String.valueOf(TEST_BELONGUSER_ID);
		mockMvc.perform(delete("/api/departments/belongs").session(mockSession).param("belongUserId", belonguserId).param("departmentId", departmentId)).andExpect(status().is(400));
	}
	
	
}

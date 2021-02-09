package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.samples.petclinic.config.TestWebConfig;
import org.springframework.samples.petclinic.configuration.GenericIdToEntityConverter;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.enums.Role;
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.DepartmentService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.validation.DepartmentValidator;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = DepartmentController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
@WebAppConfiguration
@ContextConfiguration(classes = {TestWebConfig.class, SecurityConfiguration.class})
@Import(DepartmentController.class)
public class DepartmentControllerTest {
	
	private static final int TEST_DEPARTMENT_ID = 5;
	private static final int TEST_TEAM_ID = 3;
	private static final int TEST_USER_ID = 6;



	@MockBean
    GenericIdToEntityConverter idToEntityConverter;
	
	@MockBean
	private DepartmentService departmentService;
	
	@MockBean
	private TeamService teamService;
	
	@MockBean
	private DepartmentValidator departmentValidator;
	
	@MockBean
	private BelongsService belongsService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	protected MockHttpSession mockSession;
	
	private Department department;
	private Team team;
	//private Belongs belongs;
	//private Belongs belongs2;
	private UserTW user;
	private List<Department> depfromTeam;
	
	@BeforeEach
	void setup() {
		department = new Department();
		department.setName("LSI");
		team=new Team();
		
		user = new UserTW();
		user.setId(TEST_USER_ID);
		user.setRole(Role.team_owner);
		depfromTeam = new ArrayList<>();
		team.setDepartments(depfromTeam);
		
		department.setTeam(team);
		department.setDescription("A tope jefe de equipo");
		mockSession.setAttribute("departmentId",TEST_DEPARTMENT_ID);
		mockSession.setAttribute("teamId",TEST_TEAM_ID);
		mockSession.setAttribute("userId",TEST_USER_ID);
		given(this.departmentService.findDepartmentById(TEST_DEPARTMENT_ID)).willReturn(department);		
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(team);
		
		/*
		belongs = new Belongs();
		belongs.setUserTW(user);
		//belongs2 = new Belongs();
		List<Belongs> belongsList = new ArrayList<>();
		belongsList.add(belongs);
		department.setBelongs(belongsList);
		*/
	}
	
	
	@Test
	void testCreateDepartments() throws Exception {
		String departmentJson = objectMapper.writeValueAsString(department);
		mockMvc.perform(post("/api/departments/create").session(mockSession).contentType(MediaType.APPLICATION_JSON).content(departmentJson))
		.andExpect(status().is(200));
	}
	
	@Test
	void testCreateDepartmentsWithErrors() throws Exception {
		doThrow(new DataAccessResourceFailureException("Ya Existe"))
		.when(departmentService).saveDepartment(department);
		
		String departmentJson = objectMapper.writeValueAsString(department);
		mockMvc.perform(post("/api/departments/create").session(mockSession).contentType(MediaType.APPLICATION_JSON).content(departmentJson))
		.andExpect(status().isBadRequest());
	}
	
	
	@Test
	void testGetTeamDepartments() throws Exception {
		String depjson = objectMapper.writeValueAsString(depfromTeam);
		mockMvc.perform(get("/api/departments").session(mockSession))
		.andExpect(status().isOk())
		.andExpect(content().json(depjson));
	}
	
	
	@Test
	void testGetTeamUsers() throws Exception {
		mockMvc.perform(get("/api/department/users").param("departmentId", ((Integer) TEST_DEPARTMENT_ID).toString()))
		.andExpect(status().is(200));
	}
	
	
	@Test
	void testGetMyDepartments() throws Exception {
		mockMvc.perform(get("/api/departments/mine"))
		.andExpect(status().is(200));
		
		//.andExpect(content().string(null));
	
	}
	
	@Test
	void testUpdateDepartments() throws Exception {
		department.setName("DTE");
		department.setDescription("Departamento para pruebas");
		department.setTeam(team);
		department.setId(TEST_DEPARTMENT_ID);
		String updatejson = objectMapper.writeValueAsString(department);
		
		mockMvc.perform(patch("/api/departments/update").session(mockSession).contentType(MediaType.APPLICATION_JSON).content(updatejson))
		.andExpect(status().isOk());
	}
	
	
	@Test
	void testUpdateDepartmentsWithNullValues() throws Exception {
		department.setName(null);
		department.setDescription(null);
		String updatejson = objectMapper.writeValueAsString(department);
		
		mockMvc.perform(patch("/api/departments/update").session(mockSession).contentType(MediaType.APPLICATION_JSON).content(updatejson))
		.andExpect(status().isBadRequest());
	}
	
	
	@Test
	void testDeleteDepartments() throws Exception {
		 
		mockMvc.perform(delete("/api/departments/"+TEST_DEPARTMENT_ID+"/delete"))
		.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteDepartmentsError() throws Exception {
		doThrow(new DataAccessResourceFailureException("Id no existente"))
		.when(departmentService).deleteDepartmentById(TEST_DEPARTMENT_ID);
		
		mockMvc.perform(delete("/api/departments/"+TEST_DEPARTMENT_ID+"/delete"))
		.andExpect(status().isNotFound());
	}
	
}

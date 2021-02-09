package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.samples.petclinic.config.InterceptorController;
import org.springframework.samples.petclinic.config.TestInterceptorsWebConfig;
import org.springframework.samples.petclinic.configuration.GenericIdToEntityConverter;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Belongs;
import org.springframework.samples.petclinic.model.Department;
import org.springframework.samples.petclinic.model.Milestone;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.enums.Role;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.ToDo;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.DepartmentService;
import org.springframework.samples.petclinic.service.MilestoneService;
import org.springframework.samples.petclinic.service.ParticipationService;
import org.springframework.samples.petclinic.service.ProjectService;
import org.springframework.samples.petclinic.service.ToDoService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = InterceptorController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
@WebAppConfiguration
@ContextConfiguration(classes = {TestInterceptorsWebConfig.class, SecurityConfiguration.class})
@Import(InterceptorController.class)
public class InterceptorTest {
	private static final int TEST_TEAMOWNER_ID = 1;
	private static final int TEST_EMPLOYEE_ID = 6;
	private static final int TEST_TEAM_ID = 3;
	private static final int TEST_DEPARTMENT_ID = 8;
	private static final int TEST_PROJECT_ID = 10;
	private static final int TEST_MILESTONE_ID = 7;
	private static final int TEST_TODO_ID = 4;
	@MockBean
    GenericIdToEntityConverter idToEntityConverter;

	@MockBean
    private UserTWService userTWService;

	@MockBean
	private BelongsService belongsService;

	@MockBean
	private DepartmentService departmentService;

	@MockBean
	private MilestoneService milestoneService;

	@MockBean
	private ToDoService toDoService;

	@MockBean
	private ParticipationService participationService;

	@MockBean
	private ProjectService projectService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	protected MockHttpSession mockSession;
	private UserTW juan;
	private UserTW rosa;
	private Department calidad;
	private Belongs belongsRosa;
	private Team team;
	private Project game;
	private Participation participationRosa;
	private ToDo toDo;
	
	@BeforeEach
	void setup() {
		//Team
		team=new Team();
		team.setName("NK");
		team.setId(TEST_TEAM_ID);
		//Usuario Juan
		juan = new UserTW();
		juan.setId(TEST_TEAMOWNER_ID);
		juan.setName("Juan");
		juan.setLastname("Franklin");
		juan.setEmail("andrespuertas@cyber");
		juan.setPassword("123456789");
		juan.setRole(Role.team_owner);
		juan.setTeam(team);
		//Usuario Rosa
		rosa=new UserTW();
		rosa.setName("Rosa");
		rosa.setLastname("Fabra");
		rosa.setEmail("andrespuertas@cyber");
		rosa.setPassword("123456789");
		rosa.setRole(Role.employee);
		rosa.setTeam(team);
		//Departamento Calidad
		calidad=new Department();
		calidad.setId(TEST_DEPARTMENT_ID);
		calidad.setName("Calidad");
		calidad.setDescription("Me aseguro de...");
		calidad.setTeam(team);
		//juan belong
		belongsRosa=new Belongs();
		belongsRosa.setUserTW(rosa);
		belongsRosa.setIsDepartmentManager(true);
		belongsRosa.setDepartment(calidad);
		//Proyecto game
		game=new Project();
		game.setId(TEST_PROJECT_ID);
		game.setName("Game");
		game.setDescription("Hola");
		game.setDepartment(calidad);

		//Partticipacion Juan a game
		participationRosa=new Participation();
		participationRosa.setUserTW(rosa);
		participationRosa.setProject(game);
		participationRosa.setIsProjectManager(true);
		
		//Milestone
		Milestone milestone=new Milestone();
		milestone.setId(TEST_MILESTONE_ID);
		milestone.setName("Sprint1");
		milestone.setProject(game);
		//ToDo
		toDo=new ToDo();
		toDo.setAssignee(juan);
		toDo.setTitle("Ejercicio 1");
		toDo.setMilestone(milestone);
		
		given(userTWService.findUserById(TEST_TEAMOWNER_ID)).willReturn(juan);
		given(userTWService.findUserById(TEST_EMPLOYEE_ID)).willReturn(rosa);
		given(departmentService.findDepartmentById(TEST_DEPARTMENT_ID)).willReturn(calidad);
		given(belongsService.findCurrentBelongs(TEST_EMPLOYEE_ID, TEST_DEPARTMENT_ID)).willReturn(belongsRosa);
		given(projectService.findProjectById(TEST_PROJECT_ID)).willReturn(game);
		given(participationService.findCurrentParticipation(TEST_EMPLOYEE_ID, TEST_PROJECT_ID)).willReturn(participationRosa);
		given(milestoneService.findMilestoneById(TEST_MILESTONE_ID)).willReturn(milestone);
		given(toDoService.findToDoById(TEST_TODO_ID)).willReturn(toDo);
		mockSession.setAttribute("userId", TEST_TEAMOWNER_ID);
		mockSession.setAttribute("teamId", TEST_TEAM_ID);
	}

	//LoginInterceptor
	@Test
	void testLoginInterceptorAccessAsLogerUser() throws Exception {
		mockMvc.perform(get("/api/InterceptorTest/Login").session(mockSession))
		.andExpect(status().isOk());
	}
	@Test
	void testLoginInterceptorAccessWithoutLogin() throws Exception {
		//Intentamos acceder sin sesion
		mockMvc.perform(get("/api/InterceptorTest/Login"))
		.andExpect(status().is(403));
	}
	//TeamOwnerInterceptor
	@Test
	void testTeamOwnerInterceptorAccessAsTeamOwner() throws Exception {
		mockMvc.perform(get("/api/InterceptorTest/TeamOwner").session(mockSession))
		.andExpect(status().isOk());
	}
	@Test
	void testTeamOwnerInterceptorAccessAsEmployee() throws Exception {
		mockSession.setAttribute("userId", TEST_EMPLOYEE_ID);
		mockMvc.perform(get("/api/InterceptorTest/TeamOwner").session(mockSession))
		.andExpect(status().is(403));
	}
	//DepartmentManagerInterceptor
	@Test
	void testDepartmentManagerInterceptorAccessAsTeamOwner() throws Exception {
		String departmentId=String.valueOf(TEST_DEPARTMENT_ID);
		mockMvc.perform(get("/api/InterceptorTest/DepartmentManager").session(mockSession).param("departmentId",departmentId ))
		.andExpect(status().isOk());
	}
	@Test
	void testDepartmentManagerInterceptorAccessAsDepartmenManager() throws Exception {
		mockSession.setAttribute("userId", TEST_EMPLOYEE_ID);
		String departmentId=String.valueOf(TEST_DEPARTMENT_ID);
		mockMvc.perform(get("/api/InterceptorTest/DepartmentManager").session(mockSession).param("departmentId",departmentId ))
		.andExpect(status().isOk());
	}
	@Test
	void testDepartmentManagerInterceptorAccessAsEmployee() throws Exception {
		belongsRosa.setIsDepartmentManager(false);
		given(belongsService.findCurrentBelongs(TEST_EMPLOYEE_ID, TEST_DEPARTMENT_ID)).willReturn(belongsRosa);
		mockSession.setAttribute("userId", TEST_EMPLOYEE_ID);
		String departmentId=String.valueOf(TEST_DEPARTMENT_ID);
		mockMvc.perform(get("/api/InterceptorTest/DepartmentManager").session(mockSession).param("departmentId",departmentId ))
		.andExpect(status().is(403));
	}
	
	@Test
	void testDepartmentManagerInterceptorAccessWithoutDepartmentId() throws Exception {
		mockMvc.perform(get("/api/InterceptorTest/DepartmentManager").session(mockSession))
		.andExpect(status().is(400));
	}
	@Test
	void testDepartmentManagerInterceptorAccessWithInvalidDepartmentId() throws Exception {
		mockMvc.perform(get("/api/InterceptorTest/DepartmentManager").session(mockSession).param("departmentId","30" ))
		.andExpect(status().is(400));
	}
	//ProjectManagerInterceptor
	@Test
	void testProjectManagerInterceptorAccessAsTeamOwner() throws Exception {
		String projectId=String.valueOf(TEST_PROJECT_ID);
		mockMvc.perform(get("/api/InterceptorTest/ProjectManager").session(mockSession).param("projectId",projectId ))
		.andExpect(status().isOk());
	}
	@Test
	void testProjectManagerInterceptorWithMileStone() throws Exception {
		String milestoneId=String.valueOf(TEST_MILESTONE_ID);
		mockMvc.perform(get("/api/InterceptorTest/ProjectManager").session(mockSession).param("milestoneId",milestoneId ))
		.andExpect(status().isOk());
	}
	
	@Test
	void testProjectManagerInterceptorAccessAsDepartmenManager() throws Exception {
		mockSession.setAttribute("userId", TEST_EMPLOYEE_ID);
		String projectId=String.valueOf(TEST_PROJECT_ID);
		mockMvc.perform(get("/api/InterceptorTest/ProjectManager").session(mockSession).param("projectId",projectId ))
		.andExpect(status().isOk());
	}
	@Test
	void testProjectManagerInterceptorAccessAsProjectManager() throws Exception {
		belongsRosa.setIsDepartmentManager(false);
		given(belongsService.findCurrentBelongs(TEST_EMPLOYEE_ID, TEST_DEPARTMENT_ID)).willReturn(belongsRosa);
		mockSession.setAttribute("userId", TEST_EMPLOYEE_ID);
		String projectId=String.valueOf(TEST_PROJECT_ID);
		mockMvc.perform(get("/api/InterceptorTest/ProjectManager").session(mockSession).param("projectId",projectId ))
		.andExpect(status().isOk());
	}
	@Test
	void testProjectManagerInterceptorAccessAsEmployee() throws Exception {
		belongsRosa.setIsDepartmentManager(false);
		participationRosa.setIsProjectManager(false);
		given(belongsService.findCurrentBelongs(TEST_EMPLOYEE_ID, TEST_DEPARTMENT_ID)).willReturn(belongsRosa);
		given(participationService.findCurrentParticipation(TEST_EMPLOYEE_ID, TEST_PROJECT_ID)).willReturn(participationRosa);
		mockSession.setAttribute("userId", TEST_EMPLOYEE_ID);
		String projectId=String.valueOf(TEST_PROJECT_ID);
		mockMvc.perform(get("/api/InterceptorTest/ProjectManager").session(mockSession).param("projectId",projectId ))
		.andExpect(status().is(403));
	}
	@Test
	void testProjectManagerInterceptorAccessWithAOtherTeamProject() throws Exception {
		Team team2=new Team();
		team2.setId(90);
		calidad.setTeam(team2);
		game.setDepartment(calidad);
		given(projectService.findProjectById(TEST_PROJECT_ID)).willReturn(game);
		String projectId=String.valueOf(TEST_PROJECT_ID);
		mockMvc.perform(get("/api/InterceptorTest/ProjectManager").session(mockSession).param("projectId",projectId))
		.andExpect(status().is(403));
	}@Test
	void testProjectManagerInterceptorAccessWithoutParams() throws Exception {
		mockMvc.perform(get("/api/InterceptorTest/ProjectManager").session(mockSession))
		.andExpect(status().is(400));
	}
	//ProjectEmployeeInterceptor
	@Test
	void testProjectEmployeeInterceptorAccessAsTeamOwnerWithProject() throws Exception {
		String projectId=String.valueOf(TEST_PROJECT_ID);
		mockMvc.perform(get("/api/InterceptorTest/ProjectEmployee").session(mockSession).param("projectId",projectId ))
		.andExpect(status().isOk());
	}
	@Test
	void testProjectEmployeeInterceptorAccessAsTeamOwnerWithMilestone() throws Exception {
		String milestoneId=String.valueOf(TEST_MILESTONE_ID);
		mockMvc.perform(get("/api/InterceptorTest/ProjectEmployee").session(mockSession).param("milestoneId",milestoneId ))
		.andExpect(status().isOk());
	}
	@Test
	void testProjectEmployeeInterceptorAccessTeamOwnerAsWithToDo() throws Exception {
		String toDoId=String.valueOf(TEST_TODO_ID);
		mockMvc.perform(get("/api/InterceptorTest/ProjectEmployee").session(mockSession).param("toDoId",toDoId ))
		.andExpect(status().isOk());
	}
	@Test
	void testProjectEmployeeInterceptorAccessAsDepartmenManager() throws Exception {
		given(participationService.findCurrentParticipation(TEST_EMPLOYEE_ID, TEST_PROJECT_ID)).willReturn(null);
		mockSession.setAttribute("userId", TEST_EMPLOYEE_ID);
		String projectId=String.valueOf(TEST_PROJECT_ID);
		mockMvc.perform(get("/api/InterceptorTest/ProjectEmployee").session(mockSession).param("projectId",projectId ))
		.andExpect(status().isOk());
	}
	@Test
	void testProjectEmployeeInterceptorAccessAsProjectManager() throws Exception {
		belongsRosa.setIsDepartmentManager(false);
		given(belongsService.findCurrentBelongs(TEST_EMPLOYEE_ID, TEST_DEPARTMENT_ID)).willReturn(belongsRosa);
		mockSession.setAttribute("userId", TEST_EMPLOYEE_ID);
		String projectId=String.valueOf(TEST_PROJECT_ID);
		mockMvc.perform(get("/api/InterceptorTest/ProjectEmployee").session(mockSession).param("projectId",projectId ))
		.andExpect(status().isOk());
	}
	@Test
	void testProjectEmployeeInterceptorAccessAsEmployee() throws Exception {
		belongsRosa.setIsDepartmentManager(false);
		participationRosa.setIsProjectManager(false);
		given(belongsService.findCurrentBelongs(TEST_EMPLOYEE_ID, TEST_DEPARTMENT_ID)).willReturn(belongsRosa);
		given(participationService.findCurrentParticipation(TEST_EMPLOYEE_ID, TEST_PROJECT_ID)).willReturn(participationRosa);
		mockSession.setAttribute("userId", TEST_EMPLOYEE_ID);
		String projectId=String.valueOf(TEST_PROJECT_ID);
		mockMvc.perform(get("/api/InterceptorTest/ProjectEmployee").session(mockSession).param("projectId",projectId ))
		.andExpect(status().isOk());
	}
	@Test
	void testProjectEmployeeInterceptorAccessAsTeamOwnerOtherTeam() throws Exception {
		Team team2=new Team();
		team2.setId(900);
		juan.setTeam(team2);
		given(userTWService.findUserById(TEST_TEAMOWNER_ID)).willReturn(juan);
		String projectId=String.valueOf(TEST_PROJECT_ID);
		mockMvc.perform(get("/api/InterceptorTest/ProjectEmployee").session(mockSession).param("projectId",projectId ))
		.andExpect(status().is(403));
	}
	@Test
	void testProjectEmployeeInterceptorAccessWithoutParams() throws Exception {
		mockMvc.perform(get("/api/InterceptorTest/ProjectEmployee").session(mockSession))
		.andExpect(status().is(403));
	}
	@Test
	void testProjectEmployeeInterceptorAccessWithoutBelongsAndParticipations() throws Exception {
		given(belongsService.findCurrentBelongs(TEST_EMPLOYEE_ID, TEST_DEPARTMENT_ID)).willReturn(null);
		given(participationService.findCurrentParticipation(TEST_EMPLOYEE_ID, TEST_PROJECT_ID)).willReturn(null);
		mockSession.setAttribute("userId", TEST_EMPLOYEE_ID);
		mockMvc.perform(get("/api/InterceptorTest/ProjectEmployee").session(mockSession))
		.andExpect(status().is(403));
	}
	
}

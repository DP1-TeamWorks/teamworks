package org.springframework.samples.petclinic.web;

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
	private Belongs belongs;
	private List<Project> projects;
	private List<Department> departments;

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
		//relacion con project
		part.setProject(project);
		
		//
		
		
		//part.setUserTW(userTW);

		
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

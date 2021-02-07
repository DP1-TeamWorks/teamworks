package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.samples.petclinic.config.TestWebConfig;
import org.springframework.samples.petclinic.configuration.GenericIdToEntityConverter;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Milestone;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.service.MilestoneService;
import org.springframework.samples.petclinic.service.ProjectService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = MilestoneController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
@WebAppConfiguration
@ContextConfiguration(classes = {TestWebConfig.class, SecurityConfiguration.class})
@Import(MilestoneController.class)
public class MilestoneControllerTest {
		
	private static final int TEST_MILESTONE_ID = 11;
	private static final int TEST_PROJECT_ID = 11;

	@MockBean
    GenericIdToEntityConverter idToEntityConverter;
	
	@MockBean
	private MilestoneService milestoneService;
	
	@MockBean
	private ProjectService projectService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	protected MockHttpSession mockSession;

	private Milestone septiembre;
	private Project iissi;

	@BeforeEach
	void setup() {
		septiembre = new Milestone();
		septiembre.setId(TEST_MILESTONE_ID);
		septiembre.setName("septiembre");
		septiembre.setDueFor(LocalDate.of(2021, 7, 14));

		iissi = new Project();
		septiembre.setProject(iissi);
		
		
		mockSession.setAttribute("milestoneId",TEST_MILESTONE_ID);
		mockSession.setAttribute("milestoneId",TEST_PROJECT_ID);
		given(this.milestoneService.findMilestoneById(TEST_MILESTONE_ID)).willReturn(septiembre);
		given(this.projectService.findProjectById(TEST_PROJECT_ID)).willReturn(iissi);	
	}
	
	//devuelve bien pero no se si tengo que comprobar algo mas?
	
	@Test
	void testGetNextMilestone() throws Exception{
		mockMvc.perform(get("/api/milestones/next?projectId={projectId}", TEST_PROJECT_ID)
				.session(mockSession))
		.andExpect(status().isOk());
	}
	
	@Test
	void testGetMilestones() throws Exception{
		mockMvc.perform(get("/api/milestones?projectId={projectId}", TEST_PROJECT_ID)
				.session(mockSession))
		.andExpect(status().isOk());
	}
	
	
	//tipo de las milestones??????
	@Test
	void testPostMilestones() throws Exception{
		String json = objectMapper.writeValueAsString(septiembre);
		
		mockMvc.perform(post("/api/milestones?projectId={projectId}", TEST_PROJECT_ID)
				.session(mockSession).contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}
	
	
	@Test
	void testDeleteMilestones() throws Exception{
		mockMvc.perform(delete("/api/milestones?milestoneId={milestoneId}", TEST_MILESTONE_ID)
				.session(mockSession))
		.andExpect(status().isOk());
	}
	
}

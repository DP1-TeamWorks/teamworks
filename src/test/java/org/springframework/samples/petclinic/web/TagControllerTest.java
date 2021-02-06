package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

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
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.service.ProjectService;
import org.springframework.samples.petclinic.service.TagService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = TagController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
@WebAppConfiguration
@ContextConfiguration(classes = {TestWebConfig.class, SecurityConfiguration.class})
@Import(TagController.class)
public class TagControllerTest {
		
	private static final int TEST_TAG_ID = 111;
	private static final int TEST_PROJECT_ID = 99;

	@MockBean
    GenericIdToEntityConverter idToEntityConverter;
	
	@MockBean
	private TagService tagService;
	
	@MockBean
	private ProjectService projectService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	protected MockHttpSession mockSession;

	private Tag testing;
	private Project saveTheWorld;

	@BeforeEach
	void setup() {
		testing = new Tag();
		testing.setId(TEST_TAG_ID);
		testing.setColor("green");
		testing.setTitle("testing");
		
		saveTheWorld = new Project();
		testing.setProject(saveTheWorld);
		
		mockSession.setAttribute("projectId",TEST_PROJECT_ID);
		mockSession.setAttribute("tagId",TEST_TAG_ID);
		
		given(this.tagService.findTagById(TEST_TAG_ID)).willReturn(testing);
		given(this.projectService.findProjectById(TEST_PROJECT_ID)).willReturn(saveTheWorld);
	}
	
	
	@Test
	void testGetTagsByProjectsId() throws Exception {
		mockMvc.perform(get("/api/tags?projectId={projectId}", TEST_PROJECT_ID)
				.session(mockSession))
		.andExpect(status().isOk());
	}
	
	
	// No sé porque falla, revisar
	
	@Test
	void testCreateTag() throws Exception {
		String json = objectMapper.writeValueAsString(testing);
		
		mockMvc.perform(post("/api/tags?projectId={projectId}", TEST_PROJECT_ID)
				.session(mockSession).contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
		
		
	}
	
	@Test
	void testDeleteTag() throws Exception{
		
		mockMvc.perform(delete("/api/tags?projectId={projectId}&tagId={tagId}", TEST_PROJECT_ID, TEST_TAG_ID)
				.session(mockSession))
		.andExpect(status().isOk());
	}
	
}

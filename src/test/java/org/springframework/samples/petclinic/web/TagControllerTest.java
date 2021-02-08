package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.model.ToDo;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.ParticipationService;
import org.springframework.samples.petclinic.service.ProjectService;
import org.springframework.samples.petclinic.service.TagService;
import org.springframework.samples.petclinic.service.ToDoService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.samples.petclinic.validation.TagLimitProjectException;
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
	private static final int TEST_USER_ID = 26;
	private static final int TEST_PROJECT_ID = 99;
	private static final int TEST_PARTICIPATION_ID = 88;
	private static final int TEST_TODO_ID = 88;
	@MockBean
    GenericIdToEntityConverter idToEntityConverter;
	
	@MockBean
	private TagService tagService;
	
	@MockBean
	private ProjectService projectService;
	
	@MockBean
	private UserTWService userTWService;
	
	@MockBean
	private ParticipationService participationService;
	
	@MockBean
	private ToDoService todoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	protected MockHttpSession mockSession;

	private Tag testing;
	private Project saveTheWorld;
	private UserTW maria;
	private Participation work;
	private ToDo todo;
	private Map<String, List<Tag>> map;
	private List<Tag> tags;

	@BeforeEach
	void setup() {
		testing = new Tag();
		testing.setId(TEST_TAG_ID);
		testing.setColor("#262626");
		testing.setTitle("crying");
		
		todo = new ToDo();
		List<ToDo> todos = new ArrayList<>();
		todos.add(todo);
		testing.setTodos(todos);
		
		saveTheWorld = new Project();
		saveTheWorld.setName("saveTheWorld");
		testing.setProject(saveTheWorld);
		tags = new ArrayList<>();
		tags.add(testing);
		saveTheWorld.setTags(tags);
		
		maria = new UserTW();
		maria.setId(TEST_USER_ID);
		work = new Participation();
		
		List<Participation> parts = new ArrayList<>();
		work.setProject(saveTheWorld);
		parts.add(work);
		maria.setParticipation(parts);
		saveTheWorld.setParticipations(parts);
		
		
		//cosas de getallmytagsbyproject
		
		map = new HashMap<String, List<Tag>>();
		map.put(saveTheWorld.getName(), tags);
		
		mockSession.setAttribute("projectId",TEST_PROJECT_ID);
		mockSession.setAttribute("tagId",TEST_TAG_ID);
		mockSession.setAttribute("userId",TEST_USER_ID);
		mockSession.setAttribute("participationId", TEST_PARTICIPATION_ID);
		mockSession.setAttribute("todoId", TEST_TODO_ID);
		
		given(this.tagService.findTagById(TEST_TAG_ID)).willReturn(testing);
		given(this.projectService.findProjectById(TEST_PROJECT_ID)).willReturn(saveTheWorld);
		given(this.userTWService.findUserById(TEST_USER_ID)).willReturn(maria);
		given(this.participationService.findCurrentParticipation(TEST_USER_ID, TEST_PROJECT_ID)).willReturn(work);
	}
	
	
	@Test
	void testGetTagsByProjectsId() throws Exception {
		String json = objectMapper.writeValueAsString(tags);
		
		mockMvc.perform(get("/api/tags?projectId={projectId}", TEST_PROJECT_ID)
				.session(mockSession))
		.andExpect(status().isOk())
		.andExpect(content().json(json));
	}
	
	//comprobar lo que esta devolviendo
	@Test
	void testGetAllMyTagsByProject() throws Exception{
		String json = objectMapper.writeValueAsString(map);
		
		mockMvc.perform(get("/api/tags/mine/all")
				.session(mockSession))
		.andExpect(status().isOk())
		.andExpect(content().json(json));
	}
	
	
	
	@Test
	void testCreateTag() throws Exception {
		String json = objectMapper.writeValueAsString(testing);
		
		mockMvc.perform(post("/api/tags?projectId={projectId}", TEST_PROJECT_ID)
				.session(mockSession).contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}
	
	@Test
	void testCreateTagWithErrors() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String json = objectMapper.writeValueAsString(map);
		
		mockMvc.perform(post("/api/tags?projectId={projectId}", TEST_PROJECT_ID)
				.session(mockSession).contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isBadRequest());
	}
	
	
	@Test
	void testCreateTagTooMany() throws Exception{
		
		doThrow(new TagLimitProjectException())
		.when(tagService).saveTag(testing);
		
		String json = objectMapper.writeValueAsString(testing);
		mockMvc.perform(post("/api/tags?projectId={projectId}", TEST_PROJECT_ID)
				.session(mockSession).contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void testCreateTagAlredyExist() throws Exception{
		doThrow(new DataAccessResourceFailureException("Id ya existente"))
		.when(tagService).saveTag(testing);
		String json = objectMapper.writeValueAsString(testing);

		
		mockMvc.perform(post("/api/tags?projectId={projectId}", TEST_PROJECT_ID)
				.session(mockSession).contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isBadRequest());
		
	}
	
	@Test
	void testDeleteTagById() throws Exception{
		
		mockMvc.perform(delete("/api/tags?projectId={projectId}&tagId={tagId}", TEST_PROJECT_ID, TEST_TAG_ID)
				.session(mockSession))
		.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteTagByIdNonExistent() throws Exception{
		doThrow(new DataAccessResourceFailureException("Id no existente")).when(tagService).deleteTagById(TEST_TAG_ID);
		
		
		mockMvc.perform(delete("/api/tags?projectId={projectId}&tagId={tagId}", TEST_PROJECT_ID, TEST_TAG_ID)
				.session(mockSession))
		.andExpect(status().isBadRequest());
	}
	
}

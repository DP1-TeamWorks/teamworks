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
import org.springframework.samples.petclinic.model.Milestone;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Project;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.model.ToDo;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.MilestoneService;
import org.springframework.samples.petclinic.service.TagService;
import org.springframework.samples.petclinic.service.ToDoService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.samples.petclinic.validation.ToDoMaxToDosPerAssigneeException;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ToDoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
@WebAppConfiguration
@ContextConfiguration(classes = {TestWebConfig.class, SecurityConfiguration.class})
@Import(ToDoController.class)
public class ToDoControllerTest {

	private static final int TEST_USER_ID = 2;
	private static final int TEST_MILESTONE_ID = 3;
	private static final int TEST_PARTICIPATION_ID = 23;

	private static final int TEST_TODO_ID = 24;
	private static final int TEST_TAG1_ID = 23;
	private static final int TEST_TAG2_ID = 35;


	@MockBean
    GenericIdToEntityConverter idToEntityConverter;

	@MockBean
	private ToDoService ToDoService;

	@MockBean
	private UserTWService userService;
	
	@MockBean
	private TagService tagService;

	@MockBean
	private MilestoneService milestoneService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	protected MockHttpSession mockSession;

	private ToDo todo;
	private UserTW user;

	private Milestone milestone;
	private List<Milestone> milestones;
	private Project project;
	private Participation participation;
	private List<Participation> participationList;
	private List<ToDo> toDoList;
	private Map<String, List<ToDo>> toDoByPr;
	
	private List<Integer> tagList;
	private Tag tag1;
	private Tag tag2;


	@BeforeEach
	void setup() {
		
		participation = new Participation();
		participation.setId(TEST_PARTICIPATION_ID);
		participationList = new ArrayList<>();
		participationList.add(participation);
		
		user = new UserTW();
		user.setEmail("correo@cyber");
		user.setName("Nico");
		user.setLastname("Tester");
		user.setId(TEST_USER_ID);
		user.setParticipation(participationList);
		participation.setUserTW(user);

				
		milestone = new Milestone();
		milestone.setToDos(toDoList);
		milestones = new ArrayList<>();
		milestones.add(milestone);
		milestone.setId(TEST_MILESTONE_ID);
		
		project = new Project();
		project.setName("Testing");
		project.setDescription("Pruebas y más pruebas");
		project.setParticipations(participationList);
		project.setMilestones(milestones);
		
		participation.setProject(project);
		milestone.setProject(project);

		todo = new ToDo();
		todo.setAssignee(user);
		todo.setId(TEST_TODO_ID);
		todo.setTitle("Terminar los tests");
		todo.setMilestone(milestone);
		todo.setDone(false);

		toDoList = new ArrayList<>();
		toDoList.add(todo);

		toDoByPr = new HashMap<String, List<ToDo>>();
		toDoByPr.put(project.getName(), toDoList);

		user.setToDos(toDoList);
		
		tag1 = new Tag();
		tag2 = new Tag();
		tag1.setId(TEST_TAG1_ID);
		tag1.setTitle("TAG1");
		tag1.setColor("RED");
		tag2.setId(TEST_TAG2_ID);
		tag2.setTitle("TAG2");
		tag2.setColor("BLUE");
		tag1.setProject(project);
		tag2.setProject(project);
		tag1.setTodos(toDoList);
		tag2.setTodos(toDoList);
		
		tagList = new ArrayList<>();
		tagList.add(tag1.getId());
		tagList.add(tag2.getId());

		//project.setTags(tagList);
//		todo.setTags(tagList);

		
		mockSession.setAttribute("userId",TEST_USER_ID);
		given(this.ToDoService.findToDoById(TEST_TODO_ID)).willReturn(todo);
		given(this.userService.findUserById(TEST_USER_ID)).willReturn(user);
		given(this.milestoneService.findMilestoneById(TEST_MILESTONE_ID)).willReturn(milestone);
		given(this.ToDoService.findToDoByMilestoneAndUser(TEST_MILESTONE_ID, TEST_USER_ID)).willReturn(toDoList);
		given(this.ToDoService.findToDosByMilestone(TEST_MILESTONE_ID)).willReturn(toDoList);
		given(this.ToDoService.findToDoByUser(TEST_USER_ID)).willReturn(toDoList);

		given(this.tagService.findTagById(TEST_TAG1_ID)).willReturn(tag1);
		given(this.tagService.findTagById(TEST_TAG2_ID)).willReturn(tag2);

		
	}

	@Test
	void testGetMyTodos() throws Exception {
		String toDoJson = objectMapper.writeValueAsString(toDoList);

		mockMvc.perform(get("/api/toDos/mine").param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString()).session(mockSession))
		.andExpect(status().is(200))
		.andExpect(content().json(toDoJson));

	}

	@Test
	void testGeAllMyTodosByProject() throws Exception {
		String toDoJson = objectMapper.writeValueAsString(toDoByPr);
		mockMvc.perform(get("/api/toDos/mine/all").session(mockSession))
		.andExpect(status().is(200))
		.andExpect(content().json(toDoJson));
	}
	
	@Test
	void testGeAllMyTodosFromMilestone() throws Exception {
		String toDoJson = objectMapper.writeValueAsString(toDoList);
		
		mockMvc.perform(get("/api/toDos").param("milestoneId", ((Integer)TEST_MILESTONE_ID).toString()).session(mockSession))
		.andExpect(status().is(200))
		.andExpect(content().json(toDoJson));
	}


	@Test
	void testCreateTodo() throws Exception {
		todo.setDone(false);
		String toDoJson = objectMapper.writeValueAsString(todo);

		mockMvc.perform(post("/api/toDos/create").param("mine", ((Boolean)true).toString()).param("userId", ((Integer) TEST_USER_ID).toString()).param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString()).session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))
		.andExpect(status().is(200));
	}

	@Test
	void testCreateTodo2() throws Exception {
		todo.setDone(false);
		String toDoJson = objectMapper.writeValueAsString(todo);

		//Not sending user in param
		mockMvc.perform(post("/api/toDos/create").param("mine", ((Boolean)true).toString()).param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString()).session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))
		.andExpect(status().is(200));
	}

	@Test
	void testCreateTodoWithErrors() throws Exception {
		//Bad request because of null values
		todo.setDone(null);
		todo.setTitle(null);
		String toDoJson = objectMapper.writeValueAsString(todo);

		mockMvc.perform(post("/api/toDos/create").param("mine", ((Boolean)true).toString()).param("userId", ((Integer) TEST_USER_ID).toString()).param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString()).session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))
		.andExpect(status().isBadRequest());
	}

	@Test
	void testCreateTodoWithErrors2() throws Exception {
		String toDoJson = objectMapper.writeValueAsString(todo);
		//Bad request because of not posting param
		mockMvc.perform(post("/api/toDos/create").param("mine", ((Boolean)true).toString()).param("userId", ((Integer) TEST_USER_ID).toString()).session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))
		.andExpect(status().isBadRequest());
	}

	@Test
	void testCreateTodoIdParentIncoherenceExc() throws Exception {

		String toDoJson = objectMapper.writeValueAsString(todo);

		mockMvc.perform(post("/api/toDos/create").param("mine", ((Boolean)false).toString()).param("userId", ((Integer) TEST_USER_ID).toString()).session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))
		.andExpect(status().isBadRequest());
		}


	@Test
	void testCreateTodoDataAccessExc() throws Exception {
		doThrow(new DataAccessResourceFailureException("ERROR"))
        .when(ToDoService).saveToDo(todo);

		String toDoJson = objectMapper.writeValueAsString(todo);
		//Bad request because of not posting param
		mockMvc.perform(post("/api/toDos/create").param("mine", ((Boolean)true).toString()).param("userId", ((Integer) TEST_USER_ID).toString()).session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))
		.andExpect(status().isBadRequest());
	}

	@Test
	void testCreateTodoLimitMilestoneExc() throws Exception {
		doThrow(new ToDoMaxToDosPerAssigneeException())
        .when(ToDoService).saveToDo(todo);

		String toDoJson = objectMapper.writeValueAsString(todo);
		//Bad request because of not posting param
		mockMvc.perform(post("/api/toDos/create").param("mine", ((Boolean)true).toString()).param("userId", ((Integer) TEST_USER_ID).toString()).session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))
		.andExpect(status().isBadRequest());
	}


	//ASSIGN TO DO
	@Test
	void testAssignTodo() throws Exception {

		mockMvc.perform(post("/api/toDos/assign").param("todoId", ((Integer) TEST_TODO_ID).toString())
				.param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString())
				.param("assigneeId", ((Integer) TEST_USER_ID).toString())
				.session(mockSession)).andExpect(status().is(200));
		}
	
	@Test
	void testAssignTodo2() throws Exception {
		//There's no assignee 
		mockMvc.perform(post("/api/toDos/assign").param("todoId", ((Integer) TEST_TODO_ID).toString())
				.param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString())
				.param("assigneeId", ("-1"))
				.session(mockSession)).andExpect(status().is(200));
		}
	
	@Test
	void testAssignTodoError() throws Exception {
		doThrow(new ToDoMaxToDosPerAssigneeException()).when(ToDoService).saveToDo(todo);
		//There's no assignee 
		mockMvc.perform(post("/api/toDos/assign").param("todoId", ((Integer) TEST_TODO_ID).toString())
				.param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString())
				.param("assigneeId", ((Integer) TEST_USER_ID).toString())
				.session(mockSession))
				.andExpect(status().isBadRequest());
		}
	
	@Test
	void testAssignTodoError2() throws Exception {
		doThrow(new DataAccessResourceFailureException("ERROR")).when(ToDoService).saveToDo(todo);
		//There's no assignee 
		mockMvc.perform(post("/api/toDos/assign").param("todoId", ((Integer) TEST_TODO_ID).toString())
				.param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString())
				.param("assigneeId", ((Integer) TEST_USER_ID).toString())
				.session(mockSession))
				.andExpect(status().isBadRequest());
		}
	
	
	@Test
	void testUpdateTodoTitle() throws Exception {
		todo.setTitle("New title todo");
		String toDoJson = objectMapper.writeValueAsString(todo);

		
		mockMvc.perform(post("/api/toDos/updateTitle").param("todoId", ((Integer) TEST_TODO_ID).toString())
				.param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString())
				.session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))	
				.andExpect(status().is(200));
	}
	
	
	@Test
	void testUpdateTodoNullTitleError() throws Exception {
		todo.setTitle(null);
		String toDoJson = objectMapper.writeValueAsString(todo);

		
		mockMvc.perform(post("/api/toDos/updateTitle").param("todoId", ((Integer) TEST_TODO_ID).toString())
				.param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString())
				.session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))	
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void testUpdateTodoLongTitleError() throws Exception {
		todo.setTitle("ESTE ES UN TITULO DEMASIADO LARGO POR LO QUE DARÁ ERROR");
		String toDoJson = objectMapper.writeValueAsString(todo);

		
		mockMvc.perform(post("/api/toDos/updateTitle").param("todoId", ((Integer) TEST_TODO_ID).toString())
				.param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString())
				.session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))	
				.andExpect(status().isBadRequest());
	}
	
	
	@Test
	void testUpdateTodoEmptyTitleError() throws Exception {
		todo.setTitle("");
		String toDoJson = objectMapper.writeValueAsString(todo);

		
		mockMvc.perform(post("/api/toDos/updateTitle").param("todoId", ((Integer) TEST_TODO_ID).toString())
				.param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString())
				.session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))	
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void testUpdateTodoTitleDataAccessException() throws Exception {
		doThrow(new DataAccessResourceFailureException("ERROR")).when(ToDoService).saveToDo(todo);

		todo.setTitle("New title");
		String toDoJson = objectMapper.writeValueAsString(todo);

		
		mockMvc.perform(post("/api/toDos/updateTitle").param("todoId", ((Integer) TEST_TODO_ID).toString())
				.param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString())
				.session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))	
				.andExpect(status().isBadRequest());
	}
		
	
	@Test
	void testCreatePersonalTodos() throws Exception {
		String toDoJson = objectMapper.writeValueAsString(todo);

		mockMvc.perform(post("/api/toDos/mine/create").param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString()).session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))
		.andExpect(status().is(200));
	}
	
	@Test
	void testSetTags() throws Exception {
		String toDoJson = objectMapper.writeValueAsString(tagList);

		
		mockMvc.perform(post("/api/toDos/setTags").param("todoId", ((Integer) TEST_TODO_ID).toString())
				.param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString())
				.session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))	
				.andExpect(status().is(200));
	}
	
	void testSetTagsDataAccessException() throws Exception {
		doThrow(new DataAccessResourceFailureException("ERROR"))
        .when(ToDoService).saveToDo(todo);
		
		String toDoJson = objectMapper.writeValueAsString(tagList);

		
		mockMvc.perform(post("/api/toDos/setTags").param("todoId", ((Integer) TEST_TODO_ID).toString())
				.param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString())
				.session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))	
				.andExpect(status().isBadRequest());
	}
	
	
	void testSetTagsMaxToDosException() throws Exception {
		doThrow(new ToDoMaxToDosPerAssigneeException())
        .when(ToDoService).saveToDo(todo);
		
		String toDoJson = objectMapper.writeValueAsString(tagList);

		
		mockMvc.perform(post("/api/toDos/setTags").param("todoId", ((Integer) TEST_TODO_ID).toString())
				.param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString())
				.session(mockSession).contentType(MediaType.APPLICATION_JSON).content(toDoJson))	
				.andExpect(status().isBadRequest());
	}


	@Test
	void testMarkAsDone() throws Exception {
		mockMvc.perform(post("/api/toDos/markAsDone").param("toDoId", ((Integer) TEST_TODO_ID).toString()))
		.andExpect(status().isOk());
	}


	@Test
	void testMarkAsDoneWithMilestoneException() throws Exception {
		doThrow(new ToDoMaxToDosPerAssigneeException())
        .when(ToDoService).saveToDo(todo);

		mockMvc.perform(post("/api/toDos/markAsDone")
				.param("toDoId", ((Integer) TEST_TODO_ID).toString()))
		.andExpect(status().isBadRequest());
	}


	@Test
	void testMarkAsDoneWithDataAccessException() throws Exception {
		doThrow(new DataAccessResourceFailureException("ERROR"))
        .when(ToDoService).saveToDo(todo);

		mockMvc.perform(post("/api/toDos/markAsDone").param("toDoId", ((Integer) TEST_TODO_ID).toString()))
		.andExpect(status().isBadRequest());
	}
	
	
	@Test
	void testDeleteTag() throws Exception {
		
		mockMvc.perform(delete("/api/toDos/delete")
				.param("toDoId", ((Integer) TEST_TODO_ID).toString())
				.param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString()))
				.andExpect(status().is(200));
	}
	
	
	@Test
	void testDeleteTagError() throws Exception {
		doThrow(new DataAccessResourceFailureException("ERROR"))
        .when(ToDoService).saveToDo(todo);
		
		mockMvc.perform(delete("/api/toDos/delete")
				.param("toDoId", ((Integer) TEST_TODO_ID).toString())
				.param("milestoneId", ((Integer) TEST_MILESTONE_ID).toString()))
				.andExpect(status().is(200));
	}
	
	



}

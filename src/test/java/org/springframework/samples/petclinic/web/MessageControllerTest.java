package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
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
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Milestone;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.model.ToDo;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.samples.petclinic.service.TagService;
import org.springframework.samples.petclinic.service.ToDoService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = MessageController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
@WebAppConfiguration
@ContextConfiguration(classes = {TestWebConfig.class, SecurityConfiguration.class})
@Import(MessageController.class)
public class MessageControllerTest {
	
	private static final int TEST_USER_ID = 12;
	private static final int TEST_USER2_ID = 19;
	
	//External user that doesnt send neither receive messages
	private static final int TEST_USER3_ID = 25;

	private static final int TEST_MESSAGE_ID = 21;

	private static final int TEST_TAG_ID = 25;
	private static final int TEST_TODO_ID = 17;




	@MockBean
    GenericIdToEntityConverter idToEntityConverter;
	
	@MockBean
	private MessageService messageService;
	
	@MockBean
	private UserTWService userService;
	
	@MockBean
	private TagService tagService;
	
	@MockBean
	private ToDoService toDoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	protected MockHttpSession mockSession;
	
	private UserTW sender;
	private UserTW recipient;
	private List<UserTW> recipients;

	private Message messageSent;
	private Message messageReceived;
	private List<Message> MReceivedList;
	private List<String> emails;
	private Tag tag;
	private List<Tag> tagList;
	private ToDo toDo;
	private List<ToDo> toDoList;
	private Milestone milestone;
	private String search;

	
	@BeforeEach
	void setup() {
		sender= new UserTW();
		sender.setId(TEST_USER_ID);
		sender.setName("Fer");
		sender.setLastname("Apellido");
		sender.setEmail("correo2@cyber");
		recipient =new UserTW();
		recipient.setId(TEST_USER2_ID);
		recipient.setName("Fer2");
		recipient.setLastname("Apellido2");
		recipient.setEmail("correo@cyber");
		
		recipients = new ArrayList<>(); 
		recipients.add(recipient);
		recipients.add(sender);	
		
		//recipients
		emails= new ArrayList<>();
		emails.add(recipient.getEmail());
		
		
		//MILESTONE
		milestone = new Milestone();
		milestone.setName("3er Sprint");
		milestone.setToDos(toDoList);
		
		
		//SET TAGS
		tag = new Tag();
		tag.setTitle("Testing");
		tag.setColor("GREEN");
		tag.setTodos(toDoList);
		tag.setId(TEST_TAG_ID);
		tagList = new ArrayList<>();
		tagList.add(tag);
		
		//SET TODOS
		toDo = new ToDo();
		toDo.setId(TEST_TODO_ID);
		toDo.setTitle("Terminar los tests");
		toDo.setDone(false);
		toDo.setAssignee(recipient);
		toDo.setTags(tagList);
		toDo.setMessages(MReceivedList);
		toDo.setMilestone(milestone);
		toDoList = new ArrayList<>();
		toDoList.add(toDo);
		
		
		messageSent = new Message();
		//messageReceived = new Message();
		messageSent.setId(TEST_MESSAGE_ID);
		messageSent.setSender(sender);
		messageSent.setRecipients(recipients);
		messageSent.setSubject("Pruebas de Test");
		messageSent.setText("Estamos haciendo ahora las pruebas de Test");
		messageSent.setRead(false);
		
		messageSent.setRecipientsEmails(emails);
		messageSent.setReplyTo(null);

		MReceivedList = new ArrayList<>();
		MReceivedList.add(messageSent);
		
		search = "Test";
	
		given(this.userService.findUserById(TEST_USER_ID)).willReturn(sender);		
		given(this.userService.findUserById(TEST_USER2_ID)).willReturn(recipient);	
		given(this.messageService.findMessageById(TEST_MESSAGE_ID)).willReturn(messageSent);

		given(this.messageService.findMessagesByUserId(recipient)).willReturn(MReceivedList);
		given(this.messageService.findMessagesSentByUserId(TEST_USER_ID)).willReturn(MReceivedList);
		given(this.tagService.findTagById(TEST_TAG_ID)).willReturn(tag);
		
		given(this.messageService.findMessagesByTag(recipient, tag)).willReturn(MReceivedList);
		given(this.messageService.findMessagesBySearch(recipient, search)).willReturn(MReceivedList);
		
		given(this.userService.findByEmail(recipient.getEmail())).willReturn(recipient);
	
	}
	
	
	@Test
	void testGetMyInboxMessages() throws Exception {
		mockSession.setAttribute("userId",TEST_USER2_ID);

		String json = objectMapper.writeValueAsString(MReceivedList);
		
		mockMvc.perform(get("/api/message/inbox").session(mockSession))
		.andExpect(status().is(200))
		.andExpect(content().json(json));
	}
	
	@Test
	void testGetMyInboxMessagesEmpty() throws Exception {
		mockSession.setAttribute("userId",TEST_USER3_ID);
		
		//RECEIVE A EMPTY JSON
		mockMvc.perform(get("/api/message/inbox").session(mockSession))
		.andExpect(status().is(200))
		.andExpect(content().json("[]"));
	}
	
	@Test
	void testGetMySentMessages() throws Exception {
		mockSession.setAttribute("userId",TEST_USER_ID);

		String json = objectMapper.writeValueAsString(MReceivedList);
		
		mockMvc.perform(get("/api/message/sent").session(mockSession))
		.andExpect(status().is(200))
		.andExpect(content().json(json));
	}
	
	//TODO NO LANZA LA EXCEPCIÓN
//	@Test
//	void testGetMySentMessagesError() throws Exception {
//		doThrow(new DataAccessResourceFailureException("Error")).when(messageService).findMessagesByUserId(sender);
//		mockSession.setAttribute("userId",TEST_USER_ID);
//		
//		mockMvc.perform(get("/api/message/sent").session(mockSession))
//		.andExpect(status().isBadRequest());
//	}
	
	@Test
	void testGetEmptySentMessages() throws Exception {
		mockSession.setAttribute("userId",TEST_USER3_ID);
		
		//RECEIVE A EMPTY JSON
		mockMvc.perform(get("/api/message/sent").session(mockSession))
		.andExpect(status().is(200))
		.andExpect(content().json("[]"));
	}
	
	
	
	@Test
	void testGetMessagesByTag() throws Exception {
		//messageSent.setTags(tagList);
		List<Integer> tagListInt = new ArrayList<>();
		tagListInt.add(TEST_TAG_ID);
		messageSent.setTagList(tagListInt);
		//messageSent.setToDos(toDoList);
//		List<Integer> todoListInt = new ArrayList<>();
//		todoListInt.add(TEST_TODO_ID);
//		messageSent.setTagList(todoListInt);
		
		mockSession.setAttribute("userId",TEST_USER2_ID);

		String json = objectMapper.writeValueAsString(MReceivedList);
		
		mockMvc.perform(get("/api/message/byTag").param("tagId", ((Integer)TEST_TAG_ID).toString()).session(mockSession))
		.andExpect(status().is(200))
		.andExpect(content().json(json));
	}
	
	@Test
	void testGetMessagesByTagError() throws Exception {
		List<Integer> tagListInt = new ArrayList<>();
		//tagListInt.add(TEST_TAG_ID);
		messageSent.setTagList(tagListInt);
		
		mockSession.setAttribute("userId",TEST_USER2_ID);
		
		doThrow(new DataAccessResourceFailureException("ERROR")).when(tagService).findTagById(TEST_TAG_ID);

		mockMvc.perform(get("/api/message/byTag").param("tagId", ((Integer)TEST_TAG_ID).toString()).session(mockSession))
		.andExpect(status().is(404));
	}
	
	@Test
	void testGetMessagesBySearch() throws Exception {
		mockSession.setAttribute("userId",TEST_USER2_ID);

		mockMvc.perform(get("/api/message/bySearch").param("search", search).session(mockSession))
		.andExpect(status().is(200));
	}
	
	@Test
	void testGetNumberMessagesNotRead() throws Exception {
		mockSession.setAttribute("userId",TEST_USER2_ID);
		
		mockMvc.perform(get("/api/message/noRead").session(mockSession))
		.andExpect(status().is(200))
		.andExpect(content().string(((Integer)MReceivedList.size()).toString()));
	}
	
	
	@Test
	void testGetNumberMessagesNotReadByTag() throws Exception {
		List<Integer> tagListInt = new ArrayList<>();
		tagListInt.add(TEST_TAG_ID);
		messageSent.setTagList(tagListInt);
		
		mockSession.setAttribute("userId",TEST_USER2_ID);
		
		mockMvc.perform(get("/api/message/noReadByTag").param("tagId", ((Integer)TEST_TAG_ID).toString()).session(mockSession))
		.andExpect(status().is(200))
		.andExpect(content().string(((Integer)MReceivedList.size()).toString()));
	}
	
	@Test
	void testGetNumberEmptyMessagesNotReadByTag() throws Exception {
		List<Integer> tagListInt = new ArrayList<>();
		tagListInt.add(TEST_TAG_ID);
		messageSent.setTagList(tagListInt);
		
		mockSession.setAttribute("userId",TEST_USER3_ID);
		//Recibe 0 ya que es un usuario que no tiene mensajes
		
		mockMvc.perform(get("/api/message/noReadByTag").param("tagId", ((Integer)TEST_TAG_ID).toString()).session(mockSession))
		.andExpect(status().is(200))
		.andExpect(content().string("0"));
	}
	
	@Test
	void testGetNumberMessagesNotReadByTagError() throws Exception {
		List<Integer> tagListInt = new ArrayList<>();
		tagListInt.add(TEST_TAG_ID);
		messageSent.setTagList(tagListInt);
		
		mockSession.setAttribute("userId",TEST_USER2_ID);
		doThrow(new DataAccessResourceFailureException("ERROR")).when(tagService).findTagById(TEST_TAG_ID);
		
		//DataAccessException
		mockMvc.perform(get("/api/message/noReadByTag").param("tagId", ((Integer)TEST_TAG_ID).toString()).session(mockSession))
		.andExpect(status().is(404));
	}
	
	@Test
	void testNewMessage() throws Exception {
		Message message2 = new Message();
		message2.setSender(sender);
		//message2.setRecipients(recipients);
		
		Map<String, Object> map = new HashMap<>();
		List<String> listacorreos = new ArrayList<>();
		listacorreos.add("correo@cyber");
		map.put("recipientsEmails", listacorreos);
		List<String> listatags = new ArrayList<>();
		List<String> listatoDo = new ArrayList<>();


		map.put("read", false);
		map.put("subject", "Testing");
		map.put("tagList", listatags);
		map.put("text", "Una prueba de correo");
		map.put("toDoList", listatoDo);
		
		String jsonMap = objectMapper.writeValueAsString(map);

//		List<String> lista = new ArrayList<>();
//		lista.add("correo@cyber");
//		lista.add("correo2@cyber");
//		message2.setRecipientsEmails(lista);
//		message2.setSubject("Pruebas de Test");
//		message2.setText("Estamos haciendo ahora las pruebas de Test");
//		message2.setRead(false);
//		message2.setReplyTo(null);
//		message2.setTagList(null);
//		message2.setToDoList(null);
		
		mockSession.setAttribute("userId",TEST_USER_ID);
		
		mockMvc.perform(post("/api/message").session(mockSession).contentType(MediaType.APPLICATION_JSON).content(jsonMap))
		.andExpect(status().is(200));
	}
	
	//TODO NO SALTA ERROR
//	@Test
//	void testNewMessageError() throws Exception {
//		//Send any value null in order to throw an exception
//		Map<String, Object> map = new HashMap<>();
//		List<String> listacorreos = new ArrayList<>();
//		listacorreos.add("correo@cyber");
//		map.put("recipientsEmails", listacorreos);
//		List<String> listatags = new ArrayList<>();
//		List<String> listatoDo = new ArrayList<>();
//
//
//		map.put("read", false);
////		map.put("subject", "Testing");
//		map.put("tagList", listatags);
////		map.put("text", "Una prueba de correo");
//		map.put("toDoList", listatoDo);
//		
//		String jsonMap = objectMapper.writeValueAsString(map);
//		
//		//mockSession.setAttribute("userId",TEST_USER_ID);
//		
//		mockMvc.perform(post("/api/message").contentType(MediaType.APPLICATION_JSON).content(jsonMap))
//		.andExpect(status().isBadRequest());
//	}
	
	
	//TODO PROBLEMA CON EL CONTROLLER DE MESSAGE
//	@Test
//	void testReplyMessage() throws Exception {
//		
//		Map<String, Object> map = new HashMap<>();
//		List<String> listacorreos = new ArrayList<>();
//		listacorreos.add("correo@cyber");
//		map.put("recipientsEmails", listacorreos);
//		List<String> listatags = new ArrayList<>();
//		List<String> listatoDo = new ArrayList<>();
//
//
//		map.put("read", false);
//		map.put("subject", "Testing");
//		map.put("tagList", listatags);
//		map.put("text", "Una prueba de correo");
//		map.put("toDoList", listatoDo);
//		
//		String jsonMap = objectMapper.writeValueAsString(map);
//		
//		
//		
//		mockMvc.perform(post("api/message/reply").param("replyId", ((Integer)TEST_MESSAGE_ID).toString()).session(mockSession).contentType(MediaType.APPLICATION_JSON).content(jsonMap))
//		.andExpect(status().is(200));
//		
//	}
	
	//TODO ERROR DE CONTROLLER?¿
	
//	@Test
//	void testForwardMessage() throws Exception {	
//		String json = objectMapper.writeValueAsString(messageSent);
//		
//		mockMvc.perform(post("api/message/forward").param("messageId", ((Integer)TEST_MESSAGE_ID).toString()).session(mockSession).contentType(MediaType.APPLICATION_JSON).content(json))
//		.andExpect(status().is(200));
//	}
	
	
	@Test
	void testMarkAsRead() throws Exception {
		messageSent.setRead(true);
		mockSession.setAttribute("userId",TEST_USER2_ID);

		mockMvc.perform(post("/api/message/markAsRead").param("messageId", ((Integer)TEST_MESSAGE_ID).toString()).session(mockSession))
		.andExpect(status().is(200));
	}
	
	@Test
	//Try to read a message that the user hasn't received
	void testMarkAsReadError() throws Exception {
		messageSent.setRead(true);
		mockSession.setAttribute("userId",TEST_USER3_ID);

		mockMvc.perform(post("/api/message/markAsRead").param("messageId", ((Integer)TEST_MESSAGE_ID).toString()).session(mockSession))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	//Throw an exception in order to catch it
	void testMarkAsReadError2() throws Exception {
		doThrow(new DataAccessResourceFailureException("Error")).when(messageService).saveMessage(messageSent);

		messageSent.setRead(true);
		mockSession.setAttribute("userId",TEST_USER_ID);

		mockMvc.perform(post("/api/message/markAsRead").param("messageId", ((Integer)TEST_MESSAGE_ID).toString()).session(mockSession))
		.andExpect(status().isBadRequest());
	}
	

}

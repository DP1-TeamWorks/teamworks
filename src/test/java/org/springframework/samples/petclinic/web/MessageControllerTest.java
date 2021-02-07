package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


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
import org.springframework.samples.petclinic.model.Message;
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


	
	@BeforeEach
	void setup() {
		sender= new UserTW();
		sender.setId(TEST_USER_ID);
		recipient =new UserTW();
		recipient.setId(TEST_USER2_ID);
		
		recipients = new ArrayList<>(); 
		recipients.add(recipient);
		recipients.add(sender);
		
		messageSent = new Message();
		messageReceived = new Message();
		
		messageSent.setSender(sender);
		messageSent.setRecipients(recipients);
		messageSent.setSubject("Pruebas de Test");
		messageSent.setRead(false);
		messageSent.setReplyTo(null);
		
		
		MReceivedList = new ArrayList<>();
		MReceivedList.add(messageSent);


		messageSent.setText("Estamos haciendo ahora las pruebas de Test");
		
		
		mockSession.setAttribute("userId",TEST_USER_ID);

		
		given(this.userService.findUserById(TEST_USER_ID)).willReturn(recipient);		
		given(this.userService.findUserById(TEST_USER2_ID)).willReturn(sender);				
		given(this.messageService.findMessagesByUserId(recipient)).willReturn(MReceivedList);
	}
	
	
	@Test
	void testGetMyInboxMessages() throws Exception {
		String json = objectMapper.writeValueAsString(MReceivedList);
		
		mockMvc.perform(get("/api/message/inbox").session(mockSession))
		.andExpect(status().is(200))
		.andExpect(content().json(json));
	}
	

}

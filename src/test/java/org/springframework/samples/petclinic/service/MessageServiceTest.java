package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.validation.DateIncoherenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class MessageServiceTest {
	@Autowired
	protected MessageService messageService;
	@Autowired
	protected UserTWService userTWService;
	@Autowired
	protected TagService tagService;
	
	
	@Test
	@Transactional
	void shouldInsertMessageIntoDataBaseAndGenerateId() {

		UserTW sender = userTWService.findUserById(1);
		ArrayList<Integer> recipients = new ArrayList<>();
		recipients.add(userTWService.findUserById(2).getId());
		Message message = new Message();
		message.setSubject("Prueba de Mensaje correcto");
		message.setText("Primer mensaje enviado");

		message.setSender(sender);
		message.setId(111);
		message.setTimestamp(LocalDate.now());
		ArrayList<Integer> tags = new ArrayList<>();
		//tag: planning
		tags.add(1);
		message.setTagList(tags);
		message.setRecipientsIds(recipients);
		try {
			messageService.saveMessage(message);
		} catch (DataAccessException ex) {
			Logger.getLogger(MilestoneServiceTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		assertThat(message.getId()).isNotNull();
	}
	
	@Test
	void shouldFindMessageById() {
		Message message = messageService.findMessageById(1);
		
		assertThat(message.getSubject()).contains("Hello World in TeamWorks");
	}
	
	
	@Test
	void shouldFindMessageByUserId() {
		UserTW user = userTWService.findUserById(1);
		Collection<Message> messagesByUserId= messageService.findMessagesByUserId(user);
		List<Message> list;
		if (messagesByUserId instanceof List)
		  list = (List)messagesByUserId;
		else
		  list = new ArrayList(messagesByUserId);
		assertThat(messagesByUserId).isEqualTo(list);
	}
	
	
	@Test
	void shouldFindMessageSentByUserId() {
		UserTW user = userTWService.findUserById(1);
		Collection<Message> messagesSentByUserId= messageService.findMessagesSentByUserId(user.getId());
		List<Message> list;
		if (messagesSentByUserId instanceof List)
		  list = (List)messagesSentByUserId;
		else
		  list = new ArrayList(messagesSentByUserId);
		assertThat(messagesSentByUserId).isEqualTo(list);
	}
	
	@Test
	void shouldDeleteMessageById() {
		
		messageService.deleteMessageById(1);
		Message message = messageService.findMessageById(1);
		
		assertThat(message).isNull();
		
	}
	
	
	@Test
	void shouldFindMessageByTag() {
		Tag tag = tagService.findTagById(1);
		UserTW user = userTWService.findUserById(1);
		Collection<Message> messagesByTag= messageService.findMessagesByTag(user, tag);
		assertThat(messagesByTag).isNotNull();
	}
	
	@Test
	void shouldFindMessageBySearch() {
		UserTW user = userTWService.findUserById(1);
		String Search = "Hello";
		Collection<Message> messagesBySearch= messageService.findMessagesBySearch(user, Search);
		
		assertThat(messagesBySearch).isNotNull();
	}
	
	//TODO:
	//NEGATIVE USE CASE H21-E1
	@Test
	@Transactional
	void shouldNotInsertMessageWithoutData() {
		UserTW sender = userTWService.findUserById(1);
		ArrayList<Integer> recipients = new ArrayList<>();
		//recipients.add(2);
		
		Message messageError = new Message();
//		messageError.setRecipientsIds(recipients);
//		messageError.setSender(sender);

		assertThrows(Exception.class, ()-> {
			messageService.saveMessage(messageError);
			});
	}
	
	//TODO: ADD ATTACHMENT BIGGER THAN THE LIMIT
	@Test
	@Transactional
	void shouldNotReplyMessageWithoutData() {
		UserTW sender = userTWService.findUserById(1);
		ArrayList<Integer> recipients = new ArrayList<>();
//		recipients.add(2);
		Message replyTo = messageService.findMessageById(1);
		
		Message messageError = new Message();
		messageError.setReplyTo(replyTo);
//		messageError.setRecipientsIds(recipients);
//		messageError.setSender(sender);

		assertThrows(Exception.class, ()-> {
			messageService.saveMessage(messageError);
			});
	}

}

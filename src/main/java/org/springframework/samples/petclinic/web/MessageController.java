package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Tag;
import org.springframework.samples.petclinic.model.ToDo;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MessageController {
	private final MessageService messageService;
	private final UserTWService userService;
	
	@Autowired
	public MessageController(MessageService messageService, UserTWService userService) {
		this.messageService = messageService;
		this.userService = userService;
	}
	
	@InitBinder 
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/api/message/inbox")
	public List<Message> getMyInboxMessages(HttpServletRequest r) {
		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			List<Message> messageList = (messageService.findMessagesByUserId(userId)).stream().collect(Collectors.toList());
			return messageList;
		} catch (DataAccessException d) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find messages");

		}
	}
	
	
	@GetMapping(value = "/api/message/sent")
	public List<Message> getMySentMessages(HttpServletRequest r) {
		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			List<Message> messageList = (messageService.findMessagesSentByUserId(userId)).stream().collect(Collectors.toList());
			return messageList;
		} catch (DataAccessException d) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find messages");

		}
	}
	
	//This only gets the messages that you receive regarding one tag
	@GetMapping(value = "/api/message/bytag")
	public List<Message> getMessagesByTag(@RequestBody HttpServletRequest r, @RequestBody(required = true) Tag tag) {
		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			Integer tagId = tag.getId();
			List<Message> messageList = (messageService.findMessagesByTag(userId, tagId)).stream().collect(Collectors.toList());
			return messageList;
		} catch (DataAccessException d) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find messages");

		}
	}
	
	
	
	//CHANGE ALL INTO REQUEST BODY
	@PostMapping(value = "api/message/new")
	public ResponseEntity<String> newMessage(HttpServletRequest r, @RequestBody Message message) {
		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			UserTW sender = userService.findUserById(userId);
			message.setSender(sender);
			message.setRead(false);
			
			
			List<UserTW> recipientList = new ArrayList<>();
			for (int i=0; i<message.getRecipientsIds().size(); i++){
				UserTW recipient = userService.findUserById(message.getRecipientsIds().get(i));
				recipientList.add(recipient);
			}
			message.setRecipients(recipientList);	
			messageService.saveMessage(message);
			
			return ResponseEntity.ok().build();

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping(value = "api/message/reply")
	public ResponseEntity<String> replyMessage(HttpServletRequest r, @RequestParam(required = true) Integer messageId, @RequestParam(required = true)Message message,
			@RequestParam(required = false) List<String> TodoIdList, @RequestParam(required = false) List<String> TagList) {
		try {
			Integer userId = (Integer) r.getSession().getAttribute("userId");
			UserTW sender = userService.findUserById(userId);
			message.setSender(sender);
			
			//message.setRecipients(recipientList);			
			if(TodoIdList != null) {
				
			} 
			
			//TODO REFERENCES TO-DO
			//TODO isMarked Tag
			
			//messageService.saveMessage(message);
			return ResponseEntity.ok().build();

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	
	@PostMapping(value = "api/message/forward")
	public ResponseEntity<String> forwardMessage(HttpServletRequest r, @RequestParam(required = true) Integer messageId) {
		try {
			//TODO
			return ResponseEntity.ok().build();

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	
	@PostMapping(value = "/api/message/markAsRead")
	public ResponseEntity<String> markAsRead(HttpServletRequest r, @RequestParam(required = true) Integer messageId) {
		try {
			Message message = messageService.findMessageById(messageId);
			message.setRead(true);
			return ResponseEntity.ok().build();

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}

}

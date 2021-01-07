package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping(value = "/api/messages")
	public List<Message> getMessages(@RequestParam(required = false) String user) {
		if (user == null) {
			//get messages by the user?
			//List<Message> list = messageService.getAllMessages().stream().collect(Collectors.toList());
			return null;
		} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find user");

		}
	}
	
	@PostMapping(value = "/api/messages")
	public ResponseEntity<String> postMessages(@RequestParam(required = true) Integer messageId, HttpServletRequest r) {
		try {
			//TODO 
			
			
			
			return ResponseEntity.ok("Message sent");
		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping(value = "/api/messages")
	public ResponseEntity<String> deleteMessage(@RequestParam(required = true) Integer messageId) {
		// System.out.println("Delete user: "+ userTWId);

		try {
			messageService.deleteMessageById(messageId);
			return ResponseEntity.ok("Message Deleted");

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}

	}

}

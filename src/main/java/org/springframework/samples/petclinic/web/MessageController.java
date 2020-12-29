package org.springframework.samples.petclinic.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
	private final MessageService messageService;
	//private final UserService userService;
	
	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
		
	}
	
	@InitBinder 
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
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

}

package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.service.AttatchmentService;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;

public class AttatchmentController {
	
	private final AttatchmentService attatchmentService;
	private final MessageService messageService;
	
	
	@Autowired
	public AttatchmentController(AttatchmentService attatchmentService, MessageService messageService) {
		this.attatchmentService = attatchmentService;
		this.messageService = messageService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/*
	@GetMapping(value = "/api/attatchment")
	public List<Attatchment> getAttatchments(@RequestParam(required = false) String message) {
		if (message == null) {
			List<Attatchment> list = attatchmentService.getAllUsers().stream().collect(Collectors.toList());
			return list;
		} else {
			List<UserTW> list = userService.findUserByName(user).stream().collect(Collectors.toList());
			if (list == null)
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find user");
			else {
				return list;
			}
		}
	}
	*/
	/*
	@PostMapping(value = "/api/attatchment")
	public ResponseEntity<String> postAttatchment(HttpServletRequest r, @RequestBody Attatchment attatchment, Integer messageId) {
		try {
			Message message = messageService.findMessageById(messageId);
			attatchmentService.saveAttatchment(attatchment);
			return ResponseEntity.ok("Attatchment saved");
			

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}
	*/
	@DeleteMapping(value = "/api/attatchment")
	public ResponseEntity<String> deleteAttatchment(@RequestParam(required = true) Integer attatchmentId) {

		try {
			attatchmentService.deleteAttatchmentById(attatchmentId);
			return ResponseEntity.ok("Attatchment Deleted");

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}

	}

}

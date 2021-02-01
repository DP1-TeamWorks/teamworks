package org.springframework.samples.petclinic.web;

import java.util.List;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Attachment;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.UserTW;
import org.springframework.samples.petclinic.service.AttachmentService;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class AttachmentController {
	
	private final AttachmentService attachmentService;
	private final MessageService messageService;
	
	
	@Autowired
	public AttachmentController(AttachmentService attachmentService, MessageService messageService) {
		this.attachmentService = attachmentService;
		this.messageService = messageService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@PostMapping(value = "/api/attachment")
	public ResponseEntity<String> postAttatchment(@RequestParam Integer messageId, @RequestBody Attachment attachment) {
		try {
			//Integer messageId =(Integer) r.getSession().getAttribute("messageId");
			Message message = messageService.findMessageById(messageId);
			attachment.setMessage(message);
			attachmentService.saveAttatchment(attachment);
			return ResponseEntity.ok("Attatchment saved");
			

		} catch (DataAccessException d) {
			return ResponseEntity.badRequest().build();
		}
	}
	

}
